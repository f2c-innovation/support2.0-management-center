package com.fit2cloud.support.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CompanyService {

//    @Resource
//    private OrganizationMapper organizationMapper;
//    @Resource
//    private ExtOrganizationMapper extOrganizationMapper;
//    @Resource
//    private WorkspaceMapper workspaceMapper;
//    @Resource
//    private ExtWorkspaceMapper extWorkspaceMapper;
//    @Autowired
//    private UserService userService;
//
//    public Object organizations(SessionUser sessionUser) {
//        OrganizationExample example = new OrganizationExample();
//        if (StringUtils.equals(sessionUser.getParentRoleId(), RoleConstants.Id.ORGADMIN.name())) {
//            Set<String> resourceIds = UserRoleUtils.getResourceIds(sessionUser.getId());
//            example.createCriteria().andIdIn(new ArrayList<>(resourceIds));
//        }
//        example.setOrderByClause("name");
//        return organizationMapper.selectByExample(example);
//    }
//
//    public List<OrganizationDTO> paging(Map<String, Object> map) {
//        return extOrganizationMapper.paging(map);
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void delete(List<String> organizationIds) {
//        // is has linked workspaces?
//        WorkspaceExample countExample = new WorkspaceExample();
//        countExample.createCriteria().andOrganizationIdIn(organizationIds);
//        long countWorkspace = workspaceMapper.countByExample(countExample);
//        if (countWorkspace > 0) {
//            throw new RuntimeException("请删除关联工作空间!");
//        }
//        organizationIds.forEach(organizationId -> {
//            organizationMapper.deleteByPrimaryKey(organizationId);
//            OperationLogService.log(null, organizationId, null, ResourceTypeConstants.ORGANIZATION.name(), ResourceOperation.DELETE, null);
//        });
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public Organization insert(CreateOrganizationRequest request) {
//        if (StringUtils.isBlank(request.getName())) {
//            F2CException.throwException("名称不能为空");
//        }
//        Organization organization = new Organization();
//        BeanUtils.copyBean(organization, request);
//        organization.setId(UUIDUtil.newUUID());
//        organization.setCreateTime(Instant.now().toEpochMilli());
//        try {
//            organizationMapper.insert(organization);
//            OperationLogService.log(null, organization.getId(), organization.getName(), ResourceTypeConstants.ORGANIZATION.name(), ResourceOperation.CREATE, null);
//        } catch (DuplicateKeyException e) {
//            F2CException.throwException(MessageConstants.NameDuplicateKey);
//        }
//
//        return organization;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public Organization update(UpdateOrganizationRequest request) {
//        if (StringUtils.isBlank(request.getId())) {
//            F2CException.throwException("ID不能为空");
//        }
//        if (StringUtils.isBlank(request.getName())) {
//            F2CException.throwException("名称不能为空");
//        }
//        Organization organization = new Organization();
//        BeanUtils.copyBean(organization, request);
//        try {
//            organizationMapper.updateByPrimaryKeySelective(organization);
//            OperationLogService.log(null, organization.getId(), organization.getName(), ResourceTypeConstants.ORGANIZATION.name(), ResourceOperation.UPDATE, null);
//        } catch (DuplicateKeyException e) {
//            F2CException.throwException(MessageConstants.NameDuplicateKey);
//        }
//
//        return organization;
//    }
//
//    public List<Workspace> linkWorkspacePaging(String organizationId) {
//        return extWorkspaceMapper.linkWorkspacePaging(organizationId);
//    }
//
//    public List<UserDTO> linkOrgAdminPaging(String organizationId) {
//        List<UserDTO> list = extWorkspaceMapper.linkOrgAdminPaging(organizationId);
//        userService.convertUserDTO(list, new HashMap<>());
//        return list;
//    }
//
//    public Object currentOrganization(String organizationId) {
//        Organization organization = organizationMapper.selectByPrimaryKey(organizationId);
//        return Collections.singletonList(organization);
//    }
}
