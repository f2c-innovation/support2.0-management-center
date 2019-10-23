package com.fit2cloud.support.dto.request;

import com.fit2cloud.commons.annotation.FuzzyQuery;
import io.swagger.annotations.ApiModelProperty;

/**
 * create by mgh 2019.10.23
 */
public class AgentRequest {

    @ApiModelProperty("代理商ID")
    private String id;

    @ApiModelProperty("代理商名称,模糊匹配")
    @FuzzyQuery
    private String name;

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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
