package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Department;
import com.fit2cloud.commons.server.constants.RoleConstants;
import com.fit2cloud.commons.server.model.UserDTO;
import com.fit2cloud.commons.server.utils.DepartmentUtils;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.DepartmentDTO;
import com.fit2cloud.support.dto.request.CreateDepartmentRequest;
import com.fit2cloud.support.dto.request.DepartmentRequest;
import com.fit2cloud.support.dto.request.UpdateDepartmentRequest;
import com.fit2cloud.support.service.DepartmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Author: maguohao
 * Date: 2019/10/25  上午7:16
 * Description:
 */
@Api(tags = "部门")
@RequestMapping("/department")
@RestController
public class DeptartmentController {

    @Resource
    private DepartmentService departmentService;

    /**
     * admin new user use
     *
     * @param companyId
     * @return List<Department>
     */
    @RequestMapping(value = "company/{companyId}", method = RequestMethod.GET)
    public List<Department> departmentByCompanyId(@PathVariable String companyId) {
        return departmentService.departmentByCompanyId(companyId);
    }

    @ApiOperation(value = "部门列表")
    @PostMapping(value = "/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.DEPARTMENT_READ)
    public Pager<List<DepartmentDTO>> paging(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody DepartmentRequest request) {
        Map<String, Object> map = BeanUtils.objectToMap(request);
        if (StringUtils.equals(SessionUtils.getUser().getParentRoleId(), RoleConstants.Id.ORGADMIN.name())) {
            map.put("role", RoleConstants.Id.ORGADMIN.name());
            List<String> resourceIds = DepartmentUtils.getDeptIdsByCompanyIds(SessionUtils.getCompanyId());
            map.put("resourceIds", resourceIds);
        }
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, departmentService.paging(map));
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Department> departments() {
        return departmentService.departments();
    }

    @ApiOperation(value = "创建部门")
    @PostMapping("/add")
    @RequiresPermissions(PermissionConstants.DEPARTMENT_CREATE)
    public DepartmentDTO insert(@RequestBody CreateDepartmentRequest request) {
        return departmentService.insert(request);
    }

    @ApiOperation(value = "编辑部门")
    @PostMapping("/update")
    @RequiresPermissions(PermissionConstants.DEPARTMENT_EDIT)
    public DepartmentDTO update(@RequestBody UpdateDepartmentRequest request) {
        return departmentService.update(request);
    }

    @ApiOperation(value = "删除部门")
    @GetMapping(value = "/delete/{departmentId}")
    @RequiresPermissions(PermissionConstants.DEPARTMENT_DELETE)
    public void delete(@PathVariable String departmentId) {
        departmentService.delete(departmentId);
    }

    @ApiOperation(value = "获取部门用户")
    @RequiresPermissions(PermissionConstants.DEPARTMENT_READ)
    @PostMapping(value = "user/{departmentId}/{goPage}/{pageSize}")
    public Pager<List<UserDTO>> authorizeUsersPaging(@PathVariable String departmentId, @PathVariable int goPage, @PathVariable int pageSize) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, departmentService.authorizeUsersPaging(departmentId));
    }
}
