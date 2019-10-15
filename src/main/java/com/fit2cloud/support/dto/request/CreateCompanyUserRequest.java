package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateCompanyUserRequest extends CreateUserBaseRequest {


    @ApiModelProperty(value = "公司角色ID",required = true)
    private String roleId;

    @ApiModelProperty(value = "公司ID列表,系统管理员必填、公司管理员不填")
    private List<String> companyIds;

    public List<String> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<String> companyIds) {
        this.companyIds = companyIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
