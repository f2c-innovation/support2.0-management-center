package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AddRoleToUserRequest {

    @ApiModelProperty(value = "角色ID",required = true)
    private String roleId;

    @ApiModelProperty(value = "用户ID",required = true)
    private String userId;

    @ApiModelProperty(value = "资源Id列表,系统管理员(roleId为组织管理员和工作空间用户必填)、组织管理员(roleId为工作空间用户必填)")
    private List<String> resourceIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
