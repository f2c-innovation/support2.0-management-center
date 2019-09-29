package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.*;
import com.fit2cloud.commons.server.base.mapper.RoleMapper;
import com.fit2cloud.commons.server.base.mapper.RolePermissionMapper;
import com.fit2cloud.commons.server.base.mapper.UserRoleMapper;
import com.fit2cloud.commons.server.constants.ResourceOperation;
import com.fit2cloud.commons.server.constants.ResourceTypeConstants;
import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.model.TreeNode;
import com.fit2cloud.commons.server.service.MenuService;
import com.fit2cloud.commons.server.service.ModuleService;
import com.fit2cloud.commons.server.service.OperationLogService;
import com.fit2cloud.commons.server.service.RoleCommonService;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.common.constants.MessageConstants;
import com.fit2cloud.support.dao.ext.ExtRoleMapper;
import com.fit2cloud.support.dto.RoleDTO;
import com.fit2cloud.support.dto.RoleOperate;
import com.fit2cloud.support.model.SupportTreeNode;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: chunxing
 * Date: 2018/5/22  下午12:46
 * Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService extends RoleCommonService {

    @Resource
    private ExtRoleMapper extRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private MenuService menuService;
    @Resource
    private ModuleService moduleService;

    public List<RoleDTO> paging(Map<String, Object> map) {
        return extRoleMapper.paging(map);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
        deleteOldPermission(roleId, null, true);
        UserRoleExample deleteExample = new UserRoleExample();
        deleteExample.createCriteria().andRoleIdEqualTo(roleId);
        userRoleMapper.deleteByExample(deleteExample);
        OperationLogService.log(null, roleId, null, ResourceTypeConstants.ROLE.name(), ResourceOperation.DELETE, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(RoleOperate operate) {

        operate.setId(UUIDUtil.newUUID());
        operate.setType(RoleConstants.Type.Additional.name());
        try {
            roleMapper.insert(operate);
            insertNewPermission(operate);
        } catch (DuplicateKeyException Duplicate) {
            throw new RuntimeException(MessageConstants.NameDuplicateKey);
        }
        OperationLogService.log(null, operate.getId(), operate.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.CREATE, null);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(RoleOperate operate) {
        //判断角色类型是否改变
        validateRole(operate);
        deleteOldPermission(operate.getId(), operate.getModuleIdList(), false);
        insertNewPermission(operate);
        roleMapper.updateByPrimaryKeySelective(operate);
        OperationLogService.log(null, operate.getId(), operate.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.UPDATE, null);
    }

    private void validateRole(RoleOperate operate) {
        Role role = roleMapper.selectByPrimaryKey(operate.getId());
        if (role == null) {
            F2CException.throwException("角色不存在!");
        }

        if (!role.getType().equalsIgnoreCase(operate.getType())) {
            F2CException.throwException("角色类型不能改变");
        }
    }

    private void deleteOldPermission(String roleId, List<String> moduleIdList, boolean delete) {
        RolePermissionExample deleteExample = new RolePermissionExample();
        RolePermissionExample.Criteria criteria = deleteExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        if (!delete) {
            if (CollectionUtils.isEmpty(moduleIdList)) return;
            criteria.andModuleIdIn(moduleIdList);
        }
        rolePermissionMapper.deleteByExample(deleteExample);
    }

    private void insertNewPermission(RoleOperate operate) {
        operate.getPermissionMap().forEach((moduleId, permissionList) -> {
            permissionList.forEach(permission -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setId(UUIDUtil.newUUID());
                rolePermission.setModuleId(moduleId);
                rolePermission.setPermissionId(permission);
                rolePermission.setRoleId(operate.getId());
                rolePermissionMapper.insert(rolePermission);
            });
        });
    }

    public List<Role> roles(String parentRoleId) {
        RoleExample example = new RoleExample();
        if (StringUtils.endsWithIgnoreCase(parentRoleId, RoleConstants.Id.ORGADMIN.name())) {
            example.createCriteria().andParentIdEqualTo(RoleConstants.Id.USER.name());
            example.or().andParentIdEqualTo(RoleConstants.Id.ORGADMIN.name());
        }
        example.setOrderByClause("type desc,parent_id");
        return roleMapper.selectByExample(example);
    }

    public List<SupportTreeNode> authorizePermission(String roleId) {
        RolePermissionExample example = new RolePermissionExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(example);
        Role role = roleMapper.selectByPrimaryKey(roleId);
        List<TreeNode> nodeList = menuService.getPermissionByRoleId(role.getParentId());

        for (TreeNode node : nodeList) {
            Set<String> permissions = rolePermissions.stream().filter(rolePermission ->
                    rolePermission.getModuleId().equals(node.getId())).map(RolePermission::getPermissionId).collect(Collectors.toSet());
            convertNode2Checked(node, permissions);
        }

        List<SupportTreeNode> supportTreeNodes = convertTreeNode2McTreeNode(nodeList);

        //如果是系统内置角色
        if (isSystemRole(roleId)) {
            List<Module> moduleList = moduleService.getLinkEnableModuleList();
            Set<String> moduleIds = moduleList.stream().map(Module::getId).collect(Collectors.toSet());

            for (SupportTreeNode supportTreeNode : supportTreeNodes) {
                if (!moduleIds.contains(supportTreeNode.getId())) {
                    convertNode2Checked(supportTreeNode);
                } else {
                    supportTreeNode.setType("link");
                }
            }
        }

        return supportTreeNodes;
    }

    private List<SupportTreeNode> convertTreeNode2McTreeNode(List<TreeNode> nodeList) {
        List<SupportTreeNode> supportTreeNodes = new ArrayList<>();

        for (TreeNode treeNode : nodeList) {
            supportTreeNodes.add(convertTreeNode2McTreeNode(treeNode));
        }
        return supportTreeNodes;
    }

    private SupportTreeNode convertTreeNode2McTreeNode(TreeNode treeNode) {
        SupportTreeNode supportTreeNode = new SupportTreeNode();
        BeanUtils.copyBean(supportTreeNode, treeNode);
        List<SupportTreeNode> children = new ArrayList<>();
        if (!CollectionUtils.isEmpty(treeNode.getChildren())) {
            for (TreeNode node : treeNode.getChildren()) {
                children.add(convertTreeNode2McTreeNode(node));
            }
        }
        supportTreeNode.setChildren(children);
        return supportTreeNode;
    }

    private boolean isSystemRole(String roleId) {
        return RoleConstants.Id.ADMIN.name().equals(roleId)
                || RoleConstants.Id.ORGADMIN.name().equals(roleId)
                || RoleConstants.Id.USER.name().equals(roleId);
    }


    private void convertNode2Checked(SupportTreeNode supportTreeNode) {
        supportTreeNode.setChecked(true);
        supportTreeNode.setDisabled(true);
        if (!CollectionUtils.isEmpty(supportTreeNode.getChildren())) {
            for (SupportTreeNode node : supportTreeNode.getChildren()) {
                convertNode2Checked(node);
            }
        }
    }

    private void convertNode2Checked(TreeNode treeNode, Set<String> permissions) {
        for (String permission : permissions) {
            if (permission.equals(treeNode.getId())) {
                treeNode.setChecked(true);
                break;
            }
        }
        if (!CollectionUtils.isEmpty(treeNode.getChildren())) {
            for (TreeNode node : treeNode.getChildren()) {
                convertNode2Checked(node, permissions);
            }
        }
    }

    //用户获取角色
    public List<Role> getRolesByResourceIds(Map<String, Object> param) {
        return extRoleMapper.getRolesByResourceIds(param);
    }
}
