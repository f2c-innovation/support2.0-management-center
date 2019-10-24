package com.fit2cloud.support.controller;

import com.fit2cloud.commons.server.base.domain.Role;
import com.fit2cloud.commons.server.model.TreeNode;
import com.fit2cloud.commons.server.service.MenuService;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.support.common.constants.PermissionConstants;
import com.fit2cloud.support.dto.RoleDTO;
import com.fit2cloud.support.dto.RoleOperate;
import com.fit2cloud.support.dto.request.RoleRequest;
import com.fit2cloud.support.model.McTreeNode;
import com.fit2cloud.support.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RequestMapping("role")
@RestController
@Api(tags = "角色")
public class RoleController {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

    @ApiOperation(value = "角色列表")
    @PostMapping(value = "/{goPage}/{pageSize}")
    @RequiresPermissions(PermissionConstants.ROLE_READ)
    public Pager<List<RoleDTO>> paging(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody RoleRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        roleService.paging(BeanUtils.objectToMap(request));
        return PageUtils.setPageInfo(page, roleService.paging(BeanUtils.objectToMap(request)));
    }

    @GetMapping(value = "/delete/{roleId}")
    @RequiresPermissions(PermissionConstants.ROLE_DELETE)
    public void delete(@PathVariable String roleId) {
        roleService.delete(roleId);
    }

    @PostMapping("/add")
    @RequiresPermissions(PermissionConstants.ROLE_CREATE)
    public void insert(@RequestBody RoleOperate role) {
        roleService.insert(role);
    }

    @PostMapping("/update")
    @RequiresPermissions(PermissionConstants.ROLE_EDIT)
    public void update(@RequestBody RoleOperate role) {
        roleService.update(role);
    }

    @GetMapping
    @RequiresPermissions(value = {PermissionConstants.USER_READ, PermissionConstants.USER_ADD_ROLE}, logical = Logical.OR)
    public List<Role> roles() {
        String parentRoleId = SessionUtils.getUser().getParentRoleId();
        return roleService.roles(parentRoleId);
    }

    @RequiresPermissions(PermissionConstants.ROLE_CREATE)
    @GetMapping(value = "permissionTree/{roleId}")
    public List<TreeNode> getPermissionTree(@PathVariable String roleId) {
        return menuService.getPermissionByRoleId(roleId);
    }

    @RequiresPermissions(PermissionConstants.ROLE_EDIT)
    @GetMapping(value = "authorizePermission/{roleId}")
    public List<McTreeNode> authorizePermission(@PathVariable String roleId) {
        return roleService.authorizePermission(roleId);
    }
}
