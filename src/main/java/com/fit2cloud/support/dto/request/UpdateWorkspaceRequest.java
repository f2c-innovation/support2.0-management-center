package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class UpdateWorkspaceRequest {

    @ApiModelProperty(value = "工作空间ID", required = true)
    private String id;

    @ApiModelProperty(value = "组织ID,组织管理员默认当前组织(可不填)", required = true)
    private String organizationId;

    @ApiModelProperty(value = "工作空间名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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
