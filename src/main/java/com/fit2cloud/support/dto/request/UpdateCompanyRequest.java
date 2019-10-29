package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UpdateCompanyRequest {

    @ApiModelProperty(value = "ID", required = true)
    private String id;

    @ApiModelProperty(value = "公司名称", required = true)
    private String name;

    @ApiModelProperty("代理商")
    private List<String> agentIdList;

    @ApiModelProperty("公司邮箱")
    private String email;

    @ApiModelProperty("公司电话")
    private String phone;

    @ApiModelProperty("公司地址")
    private String address;

    @ApiModelProperty("描述")
    private String content;

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

    public List<String> getAgentIdList() {
        return agentIdList;
    }

    public void setAgentIdList(List<String> agentIdList) {
        this.agentIdList = agentIdList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
