package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateOrganizationUserRequest extends CreateUserBaseRequest {


    @ApiModelProperty(value = "组织角色ID",required = true)
    private String roleId;

    @ApiModelProperty(value = "组织ID列表,系统管理员必填、组织管理员不填")
    private List<String> organizationIds;


    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
