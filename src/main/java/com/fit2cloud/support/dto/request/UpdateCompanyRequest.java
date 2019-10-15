package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class UpdateCompanyRequest {

    @ApiModelProperty(value = "ID", required = true)
    private String id;

    @ApiModelProperty(value = "公司名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
