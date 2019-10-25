package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.*;
import com.fit2cloud.commons.server.base.mapper.*;
import com.fit2cloud.commons.server.constants.ResourceOperation;
import com.fit2cloud.commons.server.constants.ResourceTypeConstants;
import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.constants.SystemUserConstants;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.model.ExcelExportRequest;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.service.OperationLogService;
import com.fit2cloud.commons.server.service.RoleCommonService;
import com.fit2cloud.commons.server.service.UserCommonService;
import com.fit2cloud.commons.server.utils.RoleUtils;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.server.utils.UserRoleUtils;
import com.fit2cloud.commons.server.utils.DepartmentUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.EncryptUtils;
import com.fit2cloud.commons.utils.ExcelExportUtils;
import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.common.constants.UserConstants;
import com.fit2cloud.support.dao.ext.ExtUserMapper;
import com.fit2cloud.support.dto.*;
import com.fit2cloud.support.dto.request.*;
import com.fit2cloud.support.dto.vo.UserAddVo;
import com.fit2cloud.support.dto.request.CreateCompanyUserRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Transactional(rollbackFor = Exception.class)
@Service("mcUserService")
public class UserService {

    @Resource
    private ExtUserMapper extUserMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserCommonService userCommonService;
    @Resource
    private RoleCommonService roleCommonService;
    @Resource
    private RoleService roleService;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private ExtraUserService extraUserService;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserKeyMapper userKeyMapper;

    public List<UserDTO> paging(Map<String, Object> map) {
        List<UserDTO> paging = extUserMapper.paging(map);
        convertUserDTO(paging, map);
        return paging;
    }

    public void convertUserDTO(List<UserDTO> list, Map<String, Object> map) {
        list.forEach(userDTO -> {
            map.put("userId", userDTO.getId());
            userDTO.setRoles(roleService.getRolesByResourceIds(map));
        });
    }


    public void delete(String userId) {
        if (StringUtils.equalsIgnoreCase(userId, SessionUtils.getUser().getId())) {
            throw new RuntimeException("用户不能删除本次登录账号！");
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            OperationLogService.log(null, userId, user.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.DELETE, null);
        }
        UserRoleExample userRoleExample = new UserRoleExample();

        if (roleCommonService.isCompanyAdmin()) {
            //当为组管理员时，删除当前公司和当前公司下的工作空间（解绑关系user_role）
            List<String> list = new ArrayList<>();
            list.add(SessionUtils.getCompanyId());
            List<Department> departments = departmentService.departmentByCompanyId(SessionUtils.getCompanyId());
            if (CollectionUtils.isNotEmpty(departments)) {
                list.addAll(departments.stream().map(Department::getId).collect(Collectors.toList()));
            }
            userRoleExample.createCriteria().andUserIdEqualTo(userId).andSourceIdIn(list);
            userRoleMapper.deleteByExample(userRoleExample);
        }
        if (roleCommonService.isAdmin()) {
            userMapper.deleteByPrimaryKey(userId);
            userRoleExample.createCriteria().andUserIdEqualTo(userId);
            userRoleMapper.deleteByExample(userRoleExample);
        }
    }


    public UserDTO insert(CreateUserRequest request) {
        if (StringUtils.isBlank(request.getSource())) {
            checkCreateUserParam(request);
            createUser(request);
            OperationLogService.log(null, request.getId(), request.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.CREATE, "");
            return userCommonService.getUserDTO(request.getId());
        } else {
            UserOperateDTO user = new UserOperateDTO();
            BeanUtils.copyBean(user, request);
            return insert(user);
        }

    }

    public UserDTO insert(UserOperateDTO user) {

        if (StringUtils.isBlank(user.getId()) || StringUtils.isBlank(user.getEmail())) {
            F2CException.throwException("用户ID、邮件不能为空");
        }

        if (SystemUserConstants.getUserId().equalsIgnoreCase(user.getId())) {
            F2CException.throwException("用户ID不能为system");
        }

        user.setCreateTime(Instant.now().toEpochMilli());
        user.setPassword(EncryptUtils.md5Encrypt(user.getPassword()).toString());
        if (StringUtils.isBlank(user.getSource())) {
            user.setSource(UserConstants.Source.LOCAL.getValue());
        }
        user.setActive(true);

        if (userMapper.selectByPrimaryKey(user.getId()) != null) {
            F2CException.throwException("用户ID已存在");
        }


        UserExample userExample = new UserExample();
        UserExample.Criteria criteriaMail = userExample.createCriteria();
        criteriaMail.andEmailEqualTo(user.getEmail());
        List<User> userList = userMapper.selectByExample(userExample);

        if (!CollectionUtils.isEmpty(userList)) {
            F2CException.throwException("用户邮箱已存在");
        }

        userMapper.insert(user);
        OperationLogService.log(null, user.getId(), user.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.CREATE, null);
        if (!CollectionUtils.isEmpty(user.getRoleInfoList())) {
            insertUserRoleInfo(user);
        }
        return userCommonService.getUserDTO(user.getId());
    }

    public void addUserRole(UserRoleOperateDTO roleOperateDTO) {
        if (CollectionUtils.isEmpty(roleOperateDTO.getUserIdList())
                || CollectionUtils.isEmpty(roleOperateDTO.getRoleInfoList())) {
            return;
        }

        roleOperateDTO.getUserIdList().forEach(userId -> {
            roleOperateDTO.getRoleInfoList().forEach(roleInfo -> {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleInfo.getRoleId());
                userRole.setUserId(userId);
                String parentId = RoleUtils.getParentId(roleInfo.getRoleId());
                if (StringUtils.equals(parentId, RoleConstants.Id.USER.name())) {
                    //查询TODO
                    roleInfo.getDeptIds().forEach(deptId -> {
                                if (!hasUserRole(userId, roleInfo.getRoleId(), deptId)) {
                                    insertUserRoleInfo(userRole, deptId, null);
                                }
                            }
                    );
                }
                if (StringUtils.equals(parentId, RoleConstants.Id.ORGADMIN.name())) {
                    roleInfo.getCompanyIds().forEach(companyId -> {
                                if (!hasUserRole(userId, roleInfo.getRoleId(), companyId)) {
                                    insertUserRoleInfo(userRole, companyId, null);
                                }
                            }
                    );
                }

                if (StringUtils.equals(parentId, RoleConstants.Id.ADMIN.name())) {
                    if (!hasUserRole(userId, roleInfo.getRoleId(), null)) {
                        insertUserRoleInfo(userRole, null, null);
                    }
                }

                if (StringUtils.equals(parentId, "other")) {
                    if (!hasUserRole(userId, roleInfo.getRoleId(), null)) {
                        insertUserRoleInfo(userRole, null, null);
                    }
                }
            });
        });
    }

    private boolean hasUserRole(String userId, String roleId, String sourceId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andRoleIdEqualTo(roleId);
        if (!StringUtils.isBlank(sourceId)) {
            criteria.andSourceIdEqualTo(sourceId);
        }

        List<UserRole> userRoleList = userRoleMapper.selectByExample(userRoleExample);

        return !CollectionUtils.isEmpty(userRoleList);
    }

    private void checkSystemAdmin(UserOperateDTO userOperate) {
        //查看默认系统管理员的个数，即roleId为ADMIN
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andRoleIdEqualTo(RoleConstants.Id.ADMIN.name());
        long countAdmin = userRoleMapper.countByExample(userRoleExample);
        if (countAdmin < 2) {
            List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleExample);
            boolean anyMatch = userRoles.stream().anyMatch(userRole -> userRole.getUserId().equals(userOperate.getId()));
            boolean isContainAdmin = userOperate.getRoleInfoList().stream().anyMatch(roleInfo -> StringUtils.equals(roleInfo.getRoleId(), RoleConstants.Id.ADMIN.name()));
            if (anyMatch && !isContainAdmin) {
                F2CException.throwException("更改此用户角色，系统将无默认系统管理员!");
            }
        }
    }


    public void update(UserOperateDTO userOperate) {
        if (StringUtils.isBlank(userOperate.getId()) || StringUtils.isBlank(userOperate.getEmail())) {
            F2CException.throwException("用户ID、邮件不能为空");
        }

        UserRoleExample deleteExample = new UserRoleExample();
        UserRoleExample.Criteria deleteCriteria = deleteExample.createCriteria();
        deleteCriteria.andUserIdEqualTo(userOperate.getId());
        if (roleCommonService.isAdmin()) {
            checkSystemAdmin(userOperate);
            //删除要编辑的用户在user_role的信息，然后reinsert
            userRoleMapper.deleteByExample(deleteExample);


        } else if (roleCommonService.isCompanyAdmin()) {
            //删除要编辑的用户在当前公司下的user_role的信息，然后reinsert
            //由于userOperate.roleInfoList 传的数据有特殊性 注意
            List<String> list = new ArrayList<>();
            list.add(SessionUtils.getCompanyId());
            List<Department> departments = departmentService.departmentByCompanyId(SessionUtils.getCompanyId());
            if (CollectionUtils.isNotEmpty(departments)) {
                list.addAll(departments.stream().map(Department::getId).collect(Collectors.toList()));
            }

            deleteCriteria.andSourceIdIn(list);
            userRoleMapper.deleteByExample(deleteExample);
        }

        //reinsert
        insertUserRoleInfo(userOperate);
        //非本地创建用户 不允许修改邮箱和名称
        if (!UserConstants.Source.LOCAL.getValue().equals(SessionUtils.getUser().getSource())) {
            userOperate.setEmail(null);
            userOperate.setName(null);
        } else {
            validateUser(userOperate);
        }
        userMapper.updateByPrimaryKeySelective(userOperate);
        OperationLogService.log(null, userOperate.getId(), userOperate.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.UPDATE, null);
    }

    private void validateUser(UserOperateDTO userOperate) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(userOperate.getEmail());
        List<User> userList = userMapper.selectByExample(userExample);
        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                if (!user.getId().equals(userOperate.getId())) {
                    F2CException.throwException("邮箱已存在");
                }
            }
        }
    }

    public void insertUserRoleInfo(UserOperateDTO userOperate) {
        for (RoleInfo roleInfo : userOperate.getRoleInfoList()) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleInfo.getRoleId());
            userRole.setUserId(userOperate.getId());

            String parentId = RoleUtils.getParentId(roleInfo.getRoleId());
            if (StringUtils.equals(parentId, RoleConstants.Id.USER.name())) {
                if (roleInfo.getDepartment()) {
                    CreateDepartmentRequest createDepartmentRequest = new CreateDepartmentRequest();
                    if (StringUtils.equalsIgnoreCase(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ORGADMIN.name())) {
                        createDepartmentRequest.setCompanyId(SessionUtils.getCompanyId());
                    } else {
                        createDepartmentRequest.setCompanyId(roleInfo.getSelectCompanyId());
                    }
                    createDepartmentRequest.setName(userOperate.getName() + "-部门");
                    createDepartmentRequest.setDescription("自动创建");
                    DepartmentDTO departmentDTO = departmentService.insert(createDepartmentRequest);
                    userRole.setId(UUIDUtil.newUUID());
                    insertUserRoleInfo(userRole, departmentDTO.getId(),userOperate);
                } else {
                    roleInfo.getDeptIds().forEach(deptId -> insertUserRoleInfo(userRole, deptId, userOperate));
                }
            }
            if (StringUtils.equals(parentId, RoleConstants.Id.ORGADMIN.name())) {
                roleInfo.getCompanyIds().forEach(companyId -> insertUserRoleInfo(userRole, companyId, userOperate));
            }
            if (StringUtils.equals(parentId, RoleConstants.Id.ADMIN.name())) {
                insertUserRoleInfo(userRole, null, userOperate);
            }

            if (StringUtils.equals(parentId, "other")) {
                insertUserRoleInfo(userRole, null, userOperate);
            }

        }

    }

    private void insertUserRoleInfo(UserRole userRole, String resourceId, UserOperateDTO userOperate) {
        String uuid = UUIDUtil.newUUID();
        userRole.setId(uuid);
        userRole.setSourceId(resourceId);
        userRoleMapper.insert(userRole);
    }

    public Object resourceIds(String userId) {
        return UserRoleUtils.getResourceIds(userId);
    }


    public void resetPassword(User user) {
        if (!"local".equals(SessionUtils.getUser().getSource())) {
            F2CException.throwException("非本地用户不能修改");
        }
        User select = userMapper.selectByPrimaryKey(user.getId());
        select.setPassword(EncryptUtils.md5Encrypt(user.getPassword()).toString());
        userMapper.updateByPrimaryKeySelective(select);
    }


    public void changeActive(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setActive(userDTO.getActive());
        if (userDTO.getRoles().stream().map(Role::getId).anyMatch(s -> s.equals(RoleConstants.Id.ADMIN.name())) && !userDTO.getActive()) {
            Long countAdmin = extUserMapper.countActivesUser(RoleConstants.Id.ADMIN.name());
            if (countAdmin > 1) {
                userMapper.updateByPrimaryKeySelective(user);
            } else {
                throw new RuntimeException("请保留最后一个系统管理员！");
            }
        } else {
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public List<RoleInfo> roleInfo(String userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        if (roleCommonService.isCompanyAdmin()) {
            List<String> resourceIds = DepartmentUtils.getDeptIdsByCompanyIds(SessionUtils.getCompanyId());
            resourceIds.add(SessionUtils.getCompanyId());
            param.put("resourceIds", resourceIds);
        }
        List<RoleInfo> roleInfos = extUserMapper.roleInfo(param);
        return roleInfos;
    }

    public List<Department> resourcePaging(String resourceType, String userId, String roleId) {
        return extUserMapper.resourcePaging(resourceType, userId, roleId);
    }

    public byte[] exportUsers(ExcelExportRequest request) throws Exception {
        Map<String, Object> params = request.getParams();
        List<ExcelExportRequest.Column> columns = request.getColumns();
        List<UserDTO> userDTOS = this.paging(params);
        List<List<Object>> data = userDTOS.parallelStream().map(userDTO -> new ArrayList<Object>() {{
            columns.forEach(column -> {
                switch (column.getKey()) {
                    case "roleName":
                        add(StringUtils.join(userDTO.getRoles().stream().map(Role::getName).collect(Collectors.toList()), ","));
                        break;
                    case "active":
                        if (userDTO.getActive()) {
                            add("启用");
                        } else {
                            add("禁用");
                        }
                        break;
                    case "user.create_time":
                        Instant instant = Instant.ofEpochMilli(userDTO.getCreateTime());
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        add(time);
                        break;
                    case "source":
                        UserConstants.Source source = UserConstants.Source.valueOf(userDTO.getSource().toUpperCase());
                        switch (source) {
                            case LOCAL:
                                add("本地创建");
                                break;
                            case EXTRA:
                                add("第三方");
                                break;
                        }
                        break;
                    default:
                        try {
                            add(MethodUtils.invokeMethod(userDTO, "get" + StringUtils.capitalize(ExcelExportUtils.underlineToCamelCase(column.getKey()))));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            });
        }}).collect(Collectors.toList());
        return ExcelExportUtils.exportExcelData("用户列表", request.getColumns().stream().map(ExcelExportRequest.Column::getValue).collect(Collectors.toList()), data);
    }


    public UserDTO createCompanyUser(CreateCompanyUserRequest request) {

        checkCreateUserParam(request);

        if (StringUtils.isBlank(request.getRoleId())) {
            F2CException.throwException("角色ID不能为空");
        }

        Role role = roleMapper.selectByPrimaryKey(request.getRoleId());
        if (role == null) {
            F2CException.throwException("角色不存在");
        }
        if (!RoleConstants.Id.ORGADMIN.name().equalsIgnoreCase(role.getParentId())) {
            F2CException.throwException("角色不是公司管理员");
        }

        if (CollectionUtils.isEmpty(request.getCompanyIds()) && roleCommonService.isAdmin()) {
            F2CException.throwException("公司ID列表不能为空");
        }

        createUser(request);

        List<String> organizationIds = new ArrayList<>();
        //添加公司 公司管理员只能创建当前公司管理员
        if (roleCommonService.isAdmin()) {
            //检验公司是否存在
            checkHasOrgIds(request.getCompanyIds());
            organizationIds.addAll(request.getCompanyIds());
        } else if (roleCommonService.isCompanyAdmin()) {
            organizationIds.add(SessionUtils.getCompanyId());
        }

        organizationIds.forEach(organizationId -> addUserRole(request, organizationId, request.getRoleId()));

        OperationLogService.log(null, request.getId(), request.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.CREATE, "");

        return userCommonService.getUserDTO(request.getId());
    }

    private void checkHasOrgIds(List<String> list) {
        for (String orgId : list) {
            Company company = companyMapper.selectByPrimaryKey(orgId);
            if (company == null) {
                F2CException.throwException("公司不存在，公司ID:" + orgId);
            }
        }
    }

    public UserDTO createDeptUser(CreateDepartmentUserRequest request) {

        checkCreateUserParam(request);

        if (StringUtils.isBlank(request.getRoleId())) {
            F2CException.throwException("角色ID不能为空");
        }

        Role role = roleMapper.selectByPrimaryKey(request.getRoleId());
        if (role == null) {
            F2CException.throwException("角色不存在");
        }
        if (!RoleConstants.Id.USER.name().equalsIgnoreCase(role.getParentId())) {
            F2CException.throwException("角色不是工作空间用户");
        }

        if (CollectionUtils.isEmpty(request.getDeptIds())) {
            F2CException.throwException("工作空间ID列表不能为空");
        }

        checkHasDeptIds(request.getDeptIds());

        //公司管理员只能添加有权限的工作空间
        if (roleCommonService.isCompanyAdmin()) {
            List<String> resourceIds = DepartmentUtils.getDeptIdsByCompanyIds(SessionUtils.getCompanyId());
            for (String workspaceId : request.getDeptIds()) {
                if (!resourceIds.contains(workspaceId)) {
                    F2CException.throwException("工作空间ID[" + workspaceId + "]不属于当前公司");
                }
            }
        }


        createUser(request);

        //添加工作空间
        List<String> workspaceIds = request.getDeptIds();

        workspaceIds.forEach(workspaceId -> addUserRole(request, workspaceId, request.getRoleId()));
        OperationLogService.log(null, request.getId(), request.getName(), ResourceTypeConstants.USER.name(), ResourceOperation.CREATE, "");

        return userCommonService.getUserDTO(request.getId());
    }

    private void checkHasDeptIds(List<String> list) {
        for (String workspaceId : list) {
            Department department = departmentService.getDepartmentById(workspaceId);
            if (department == null) {
                F2CException.throwException("工作空间不存在，工作空间ID:" + workspaceId);
            }
        }
    }


    private void addUserRole(CreateUserBaseRequest request, String sourceId, String roleId) {
        UserRole userRole = new UserRole();
        userRole.setId(UUIDUtil.newUUID());
        userRole.setRoleId(roleId);
        userRole.setSourceId(sourceId);
        userRole.setUserId(request.getId());
        userRoleMapper.insertSelective(userRole);
    }

    public void createUser(CreateUserBaseRequest request) {
        User user = new User();
        BeanUtils.copyBean(user, request);
        user.setSource(UserConstants.Source.LOCAL.getValue());
        user.setPassword(EncryptUtils.md5Encrypt(user.getPassword()).toString());
        user.setCreateTime(System.currentTimeMillis());
        userMapper.insertSelective(user);
    }

    private void checkCreateUserParam(CreateUserBaseRequest request) {
        if (StringUtils.isBlank(request.getId())) {
            F2CException.throwException("用户ID");
        }

        if (SystemUserConstants.getUserId().equalsIgnoreCase(request.getId())) {
            F2CException.throwException("用户ID不能为system");
        }

        if (StringUtils.isBlank(request.getName())) {
            F2CException.throwException("用户名不能为空");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            F2CException.throwException("密码不能为空");
        }

        if (userMapper.selectByPrimaryKey(request.getId()) != null) {
            F2CException.throwException("用户ID已存在");
        }
    }


    public UserDTO addRoleToUser(AddRoleToUserRequest request) {
        if (StringUtils.isBlank(request.getRoleId())) {
            F2CException.throwException("角色ID不能为空");
        }
        if (StringUtils.isBlank(request.getUserId())) {
            F2CException.throwException("用户ID不能为空");
        }

        User user = userMapper.selectByPrimaryKey(request.getUserId());
        if (user == null) {
            F2CException.throwException("用户不存在");
        }

        Role role = roleMapper.selectByPrimaryKey(request.getRoleId());

        if (role == null) {
            F2CException.throwException("角色不存在");
        }

        if (RoleConstants.Id.ADMIN.name().equalsIgnoreCase(role.getParentId())
                && !RoleConstants.Id.ADMIN.name().equalsIgnoreCase(SessionUtils.getUser().getParentRoleId())) {
            F2CException.throwException("非系统管理员不能添加系统管理员角色");
        }

        if (RoleConstants.Id.USER.name().equalsIgnoreCase(role.getParentId())) {
            if (CollectionUtils.isEmpty(request.getResourceIds())) {
                F2CException.throwException("resourceIds不能为空(工作空间ID)");
            }
            if (roleCommonService.isCompanyAdmin()) {
                checkCurrentOrgHasWorkspaceIds(request.getResourceIds());
            }
            checkHasDeptIds(request.getResourceIds());
        } else if (RoleConstants.Id.ORGADMIN.name().equalsIgnoreCase(role.getParentId())) {
            //分系统管理员和公司管理员
            if (roleCommonService.isCompanyAdmin()) {
                //公司管理员只能添加当前公司
                List<String> list = new ArrayList<>();
                list.add(SessionUtils.getCompanyId());
                request.setResourceIds(list);
            } else {
                if (CollectionUtils.isEmpty(request.getResourceIds())) {
                    F2CException.throwException("resourceIds不能为空(公司ID)");
                }
                checkHasOrgIds(request.getResourceIds());
            }
        } else {
            request.setResourceIds(null);
        }

        addUserRole(request);

        return userCommonService.getUserDTO(request.getUserId());
    }

    private void checkCurrentOrgHasWorkspaceIds(List<String> list) {
        List<String> workspaceIds = DepartmentUtils.getDeptIdsByCompanyIds(SessionUtils.getCompanyId());
        for (String workspaceId : list) {
            if (!workspaceIds.contains(workspaceId)) {
                F2CException.throwException("工作空间ID[" + workspaceId + "]不属于当前公司");
            }
        }
    }

    private void addUserRole(AddRoleToUserRequest request) {
        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(request.getUserId());
        criteria.andRoleIdEqualTo(request.getRoleId());

        if (CollectionUtils.isEmpty(request.getResourceIds())) {
            if (CollectionUtils.isEmpty(userRoleMapper.selectByExample(userRoleExample))) {
                UserRole userRole = new UserRole();
                userRole.setId(UUIDUtil.newUUID());
                userRole.setRoleId(request.getRoleId());
                userRole.setUserId(request.getUserId());
                userRoleMapper.insertSelective(userRole);
            }
        } else {
            request.getResourceIds().forEach(resourceId -> {
                criteria.andSourceIdEqualTo(resourceId);
                if (CollectionUtils.isEmpty(userRoleMapper.selectByExample(userRoleExample))) {
                    UserRole userRole = new UserRole();
                    userRole.setId(UUIDUtil.newUUID());
                    userRole.setRoleId(request.getRoleId());
                    userRole.setSourceId(resourceId);
                    userRole.setUserId(request.getUserId());
                    userRoleMapper.insertSelective(userRole);
                }
            });
        }
    }


    public UserDTO deleteRoleFromUser(DeleteRoleFromUserRequest request) {
        if (StringUtils.isBlank(request.getRoleId())) {
            F2CException.throwException("角色ID不能为空");
        }
        if (StringUtils.isBlank(request.getUserId())) {
            F2CException.throwException("用户ID不能为空");
        }

        Role role = roleMapper.selectByPrimaryKey(request.getRoleId());
        if (role == null) {
            F2CException.throwException("角色不存在");
        }

        User user = userMapper.selectByPrimaryKey(request.getUserId());
        if (user == null) {
            F2CException.throwException("用户不存在");
        }

        if (RoleConstants.Id.ADMIN.name().equalsIgnoreCase(role.getParentId())
                && !RoleConstants.Id.ADMIN.name().equalsIgnoreCase(SessionUtils.getUser().getParentRoleId())) {
            F2CException.throwException("非系统管理员不能删除系统管理员角色");
        }

        if (RoleConstants.Id.USER.name().equalsIgnoreCase(role.getParentId())) {
            if (CollectionUtils.isEmpty(request.getResourceIds())) {
                F2CException.throwException("resourceIds不能为空(工作空间ID)");
            }
            if (roleCommonService.isCompanyAdmin()) {
                checkCurrentOrgHasWorkspaceIds(request.getResourceIds());
            }
            checkHasDeptIds(request.getResourceIds());
        } else if (RoleConstants.Id.ORGADMIN.name().equalsIgnoreCase(role.getParentId())) {
            if (roleCommonService.isCompanyAdmin()) {
                //公司管理员只能删除当前公司
                List<String> list = new ArrayList<>();
                request.setResourceIds(list);
                list.add(SessionUtils.getCompanyId());
            } else {
                if (CollectionUtils.isEmpty(request.getResourceIds())) {
                    F2CException.throwException("resourceIds不能为空(公司ID)");
                }
                checkHasOrgIds(request.getResourceIds());
            }
        } else {
            request.setResourceIds(null);
        }

        deleteUserRole(request);

        return userCommonService.getUserDTO(request.getUserId());
    }

    private void deleteUserRole(DeleteRoleFromUserRequest request) {

        UserRoleExample userRoleExample = new UserRoleExample();
        UserRoleExample.Criteria criteria = userRoleExample.createCriteria();
        criteria.andUserIdEqualTo(request.getUserId());
        criteria.andRoleIdEqualTo(request.getRoleId());

        if (CollectionUtils.isEmpty(request.getResourceIds())) {
            userRoleMapper.deleteByExample(userRoleExample);
        } else {
            request.getResourceIds().forEach(resourceId -> {
                criteria.andSourceIdEqualTo(resourceId);
                userRoleMapper.deleteByExample(userRoleExample);
            });
        }
    }
}
