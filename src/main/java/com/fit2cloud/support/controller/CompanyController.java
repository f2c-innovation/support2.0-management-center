package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Company;
import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.model.SessionUser;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.CompanyDTO;
import com.fit2cloud.support.dto.request.CompanyRequest;
import com.fit2cloud.support.dto.request.CreateCompanyRequest;
import com.fit2cloud.support.dto.request.UpdateCompanyRequest;
import com.fit2cloud.support.service.CompanyService;
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

    @Resource
    private CompanyService companyService;

    @RequiresPermissions(value = {PermissionConstants.USER_READ,
            PermissionConstants.DEPARTMENT_EDIT,
            PermissionConstants.DEPARTMENT_CREATE,}, logical = Logical.OR)
    @RequestMapping(method = RequestMethod.GET)
    public Object Companys() {
        SessionUser sessionUser = SessionUtils.getUser();
        return companyService.companies(sessionUser);
    }

    @GetMapping("/currentCompany")
    public Object currentCompany() {
        SessionUser sessionUser = SessionUtils.getUser();
        return companyService.currentCompany(sessionUser.getCompanyId());
    }

    @ApiOperation(value = "公司列表")
    @PostMapping(value = "/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.COMPANY_READ)
    public Pager<List<CompanyDTO>> paging(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody CompanyRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, companyService.paging(request));
    }

    @RequiresPermissions(PermissionConstants.ROLE_READ)
    @PostMapping(value = "link/department/{companyId}/{goPage}/{pageSize}")
    public Pager<List<Department>> linkDepartmentPaging(@PathVariable String companyId, @PathVariable int goPage, @PathVariable int pageSize) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, companyService.linkDepartmentPaging(companyId));
    }

    @ApiOperation(value = "获取公司管理员")
    @RequiresPermissions(PermissionConstants.ROLE_READ)
    @PostMapping(value = "user/{companyId}/{goPage}/{pageSize}")
    public Pager<List<UserDTO>> linkCompanyAdminPaging(@PathVariable String companyId, @PathVariable int goPage, @PathVariable int pageSize) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, companyService.linkCompanyAdminPaging(companyId));
    }

    @ApiOperation(value = "批量删除公司")
    @PostMapping(value = "/delete")
    @RequiresPermissions(PermissionConstants.COMPANY_DELETE)
    public void delete(@RequestBody List<String> companyIds) {
        companyService.delete(companyIds);
    }

    @ApiOperation(value = "创建公司")
    @PostMapping("/add")
    @RequiresPermissions(PermissionConstants.COMPANY_CREATE)
    public Company insert(@RequestBody CreateCompanyRequest request) {
        return companyService.insert(request);
    }

    @ApiOperation(value = "编辑公司")
    @PostMapping("/update")
    @RequiresPermissions(PermissionConstants.COMPANY_EDIT)
    public Company update(@RequestBody UpdateCompanyRequest request) {
        return companyService.update(request);
    }

}
