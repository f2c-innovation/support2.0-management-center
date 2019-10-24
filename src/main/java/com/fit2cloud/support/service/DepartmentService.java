package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.Company;
import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.base.domain.DepartmentExample;
import com.fit2cloud.commons.server.base.domain.UserRoleExample;
import com.fit2cloud.commons.server.base.mapper.CompanyMapper;
import com.fit2cloud.commons.server.base.mapper.DepartmentMapper;
import com.fit2cloud.commons.server.base.mapper.UserRoleMapper;
import com.fit2cloud.commons.server.constants.RedisConstants;
import com.fit2cloud.commons.server.constants.ResourceOperation;
import com.fit2cloud.commons.server.constants.ResourceTypeConstants;
import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.redis.subscribe.RedisMessagePublisher;
import com.fit2cloud.commons.server.service.OperationLogService;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.dao.ext.ExtDepartmentMapper;
import com.fit2cloud.support.dto.DepartmentDTO;
import com.fit2cloud.support.dto.request.CreateDepartmentRequest;
import com.fit2cloud.support.dto.request.UpdateDepartmentRequest;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private ExtDepartmentMapper extDepartmentMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Autowired
    private UserService userService;
    @Resource
    private RedisMessagePublisher redisMessagePublisher;

    public List<Department> departmentByCompanyId(String companyId) {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andCompanyIdEqualTo(companyId);
        return departmentMapper.selectByExample(departmentExample);
    }

    public List<Department> departments() {
        DepartmentExample example = new DepartmentExample();
        if (StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ORGADMIN.name())) {
            example.createCriteria().andCompanyIdEqualTo(SessionUtils.getCompanyId());
        }
        example.setOrderByClause("name");
        return departmentMapper.selectByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    public DepartmentDTO insert(CreateDepartmentRequest request) {


        if (StringUtils.isBlank(request.getName())) {
            F2CException.throwException("名称必填");
        }

        if (StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ADMIN.name())
                && StringUtils.isBlank(request.getCompanyId())) {
            F2CException.throwException("公司ID不能为空");
        }

        Company company = companyMapper.selectByPrimaryKey(request.getCompanyId());
        if (company == null && StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ADMIN.name())) {
            F2CException.throwException("对应的公司不存在");
        }

        //判断名称是否存在
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andNameEqualTo(request.getName());

        List<Department> departments = departmentMapper.selectByExample(departmentExample);
        if (!CollectionUtils.isEmpty(departments)) {
            F2CException.throwException("部门名字存在");
        }

        Department department = new Department();
        BeanUtils.copyBean(department, request);

        String deptId = UUIDUtil.newUUID();
        department.setId(deptId);
        department.setCreateTime(Instant.now().toEpochMilli());
        if (StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ORGADMIN.name())) {
            department.setCompanyId(SessionUtils.getCompanyId());
        }
        departmentMapper.insert(department);

        OperationLogService.log(null, department.getId(), department.getName(), ResourceTypeConstants.DEPARTMENT.name(), ResourceOperation.CREATE, null);

        return getDepartmentDTOById(deptId);
    }

    @Transactional(rollbackFor = Exception.class)
    public DepartmentDTO update(UpdateDepartmentRequest request) {

        if (StringUtils.isBlank(request.getId())) {
            F2CException.throwException("工作空间ID不能为空");
        }

        if (StringUtils.isBlank(request.getName())) {
            F2CException.throwException("名称必填");
        }

        if (StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ADMIN.name())
                && StringUtils.isBlank(request.getCompanyId())) {
            F2CException.throwException("公司ID不能为空");
        }

        Company organization = companyMapper.selectByPrimaryKey(request.getCompanyId());
        if (organization == null) {
            F2CException.throwException("对应的公司不存在");
        }

        //判断名称是否存在
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria().andNameEqualTo(request.getName()).andIdNotEqualTo(request.getId());

        List<Department> workspaces = departmentMapper.selectByExample(departmentExample);
        if (!CollectionUtils.isEmpty(workspaces)) {
            F2CException.throwException("部门名字重复");
        }

        if (departmentMapper.selectByPrimaryKey(request.getId()) == null) {
            F2CException.throwException("部门不存在");
        }

        Department department = new Department();
        BeanUtils.copyBean(department, request);

        departmentMapper.updateByPrimaryKeySelective(department);
        OperationLogService.log(null, department.getId(), department.getName(), ResourceTypeConstants.DEPARTMENT.name(), ResourceOperation.UPDATE, null);
        return getDepartmentDTOById(department.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete(String workspaceId) {
        // delete be linked user_role
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andSourceIdEqualTo(workspaceId);
        userRoleMapper.deleteByExample(example);
        departmentMapper.deleteByPrimaryKey(workspaceId);
        OperationLogService.log(null, workspaceId, null, ResourceTypeConstants.DEPARTMENT.name(), ResourceOperation.CREATE, null);
        redisMessagePublisher.publish(RedisConstants.Topic.DEPARTMENT_DELETE, workspaceId);

    }

    private DepartmentDTO getDepartmentDTOById(String id) {
        List<DepartmentDTO> workspaces = paging(ImmutableMap.of("id", id));
        if (!CollectionUtils.isEmpty(workspaces)) {
            return workspaces.get(0);
        }

        return null;
    }

    public List<DepartmentDTO> paging(Map<String, Object> map) {
        return extDepartmentMapper.paging(map);
    }

    public Department getDepartmentById(String id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public Department getDepartmentByName(String deptName) {
        DepartmentExample example = new DepartmentExample();
        example.createCriteria().andNameEqualTo(deptName);
        List<Department> workspaces = this.departmentMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(workspaces)) {
            return workspaces.get(0);
        }
        return null;
    }

    public List<UserDTO> authorizeUsersPaging(String workspaceId) {
        List<UserDTO> list = extDepartmentMapper.authorizeUsersPaging(workspaceId);
        userService.convertUserDTO(list, new HashMap<>());
        return list;
    }

}
