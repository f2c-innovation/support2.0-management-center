package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateDepartmentUserRequest extends CreateUserBaseRequest {

    @ApiModelProperty(value = "部门角色ID", required = true)
    private String roleId;

    @ApiModelProperty(value = "部门ID列表", required = true)
    private List<String> deptIds;

    public List<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
