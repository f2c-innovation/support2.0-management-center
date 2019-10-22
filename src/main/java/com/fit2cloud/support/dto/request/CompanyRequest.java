package com.fit2cloud.support.dto.request;

import com.fit2cloud.commons.annotation.FuzzyQuery;
import io.swagger.annotations.ApiModelProperty;

public class CompanyRequest {

    @ApiModelProperty("公司ID")
    private String id;

    @ApiModelProperty("公司名称,模糊匹配")
    @FuzzyQuery
    private String name;

    @ApiModelProperty("公司邮箱,模糊匹配")
    @FuzzyQuery
    private String email;

    @ApiModelProperty(value = "排序Key", hidden = true)
    private String sort;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
