package com.fit2cloud.support.dto.request;

import com.fit2cloud.commons.annotation.FuzzyQuery;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DepartmentRequest {

    @ApiModelProperty("部门ID")
    private String id;

    @ApiModelProperty("部门名称,模糊匹配")
    @FuzzyQuery
    private String name;

    @ApiModelProperty("公司ID")
    private String companyId;

    @ApiModelProperty(hidden = true)
    private List<String> resourceIds;

    @ApiModelProperty(hidden = true)
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
