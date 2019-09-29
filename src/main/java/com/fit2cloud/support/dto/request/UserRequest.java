package com.fit2cloud.support.dto.request;

import com.fit2cloud.commons.annotation.FuzzyQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserRequest {

    @ApiModelProperty("用户ID，模糊匹配")
    @FuzzyQuery
    private String id;

    @ApiModelProperty("用户名称，模糊匹配")
    @FuzzyQuery
    private String name;

    @ApiModelProperty("用户邮箱，模糊匹配")
    @FuzzyQuery
    private String email;

    @ApiModelProperty("角色Id，精确匹配")
    private String roleId;

    @ApiModelProperty(value = "排序Key", hidden = true)
    private String sort;

    @ApiModelProperty(hidden = true)
    private List<String> resourceIds;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }
}
