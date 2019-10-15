package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Organization;
import com.fit2cloud.commons.server.base.domain.Workspace;
import com.fit2cloud.commons.server.model.SessionUser;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/company")
@RestController
@Api(tags = "公司")
public class CompanyController {

//    @Resource
//    private OrganizationService organizationService;
//
//
//    @RequiresPermissions(value = {PermissionConstants.USER_READ,
//            PermissionConstants.WORKSPACE_EDIT,
//            PermissionConstants.WORKSPACE_CREATE,}, logical = Logical.OR)
//    @RequestMapping(method = RequestMethod.GET)
//    public Object organizations() {
//        SessionUser sessionUser = SessionUtils.getUser();
//        return organizationService.organizations(sessionUser);
//    }
//
//    @GetMapping("/currentOrganization")
//    public Object currentOrganization() {
//        SessionUser sessionUser = SessionUtils.getUser();
//        return organizationService.currentOrganization(sessionUser.getOrganizationId());
//    }
//
//    @ApiOperation(value = "组织列表")
//    @PostMapping(value = "/{goPage}/{pageSize}")
//    @RequiresPermissions(PermissionConstants.ORGANIZATION_READ)
//    public Pager<List<OrganizationDTO>> paging(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody OrganizationRequest request) {
//        Page page = PageHelper.startPage(goPage, pageSize, true);
//        return PageUtils.setPageInfo(page, organizationService.paging(BeanUtils.objectToMap(request)));
//    }
//
//    @RequiresPermissions(PermissionConstants.ROLE_READ)
//    @PostMapping(value = "link/workspace/{organizationId}/{goPage}/{pageSize}")
//    public Pager<List<Workspace>> linkWorkspacePaging(@PathVariable String organizationId, @PathVariable int goPage, @PathVariable int pageSize) {
//        Page page = PageHelper.startPage(goPage, pageSize, true);
//        return PageUtils.setPageInfo(page, organizationService.linkWorkspacePaging(organizationId));
//    }
//
//    @ApiOperation(value = "获取组织管理员")
//    @RequiresPermissions(PermissionConstants.ROLE_READ)
//    @PostMapping(value = "user/{organizationId}/{goPage}/{pageSize}")
//    public Pager<List<UserDTO>> linkOrgAdminPaging(@PathVariable String organizationId, @PathVariable int goPage, @PathVariable int pageSize) {
//        Page page = PageHelper.startPage(goPage, pageSize, true);
//        return PageUtils.setPageInfo(page, organizationService.linkOrgAdminPaging(organizationId));
//    }
//
//    @ApiOperation(value = "批量删除组织")
//    @PostMapping(value = "/delete")
//    @RequiresPermissions(PermissionConstants.ORGANIZATION_DELETE)
//    public void delete(@RequestBody List<String> organizationIds) {
//        organizationService.delete(organizationIds);
//    }
//
//    @ApiOperation(value = "创建组织")
//    @PostMapping("/add")
//    @RequiresPermissions(PermissionConstants.ORGANIZATION_CREATE)
//    public Organization insert(@RequestBody CreateOrganizationRequest request) {
//        return organizationService.insert(request);
//    }
//
//    @ApiOperation(value = "编辑组织")
//    @PostMapping("/update")
//    @RequiresPermissions(PermissionConstants.ORGANIZATION_EDIT)
//    public Organization update(@RequestBody UpdateOrganizationRequest request) {
//        return organizationService.update(request);
//    }

}
