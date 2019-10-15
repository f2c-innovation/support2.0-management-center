package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class CreateDepartmentRequest {

    @ApiModelProperty(value = "公司ID,公司管理员默认当前组织(可不填)", required = true)
    private String companyId;

    @ApiModelProperty(value = "部门名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String description;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
