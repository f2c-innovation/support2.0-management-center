package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateWorkspaceUserRequest extends CreateUserBaseRequest {

    @ApiModelProperty(value = "工作空间角色ID", required = true)
    private String roleId;

    @ApiModelProperty(value = "工作空间ID列表", required = true)
    private List<String> workspaceIds;

    public List<String> getWorkspaceIds() {
        return workspaceIds;
    }

    public void setWorkspaceIds(List<String> workspaceIds) {
        this.workspaceIds = workspaceIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
