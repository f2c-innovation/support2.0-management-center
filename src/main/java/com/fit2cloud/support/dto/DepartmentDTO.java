package com.fit2cloud.support.dto;

import com.fit2cloud.commons.server.base.domain.Department;
import io.swagger.annotations.ApiModelProperty;

public class DepartmentDTO extends Department {

    @ApiModelProperty("授权用户数量")
    private long countAuthorizedUser;
    @ApiModelProperty("公司名称")
    private String companyName;

    public long getCountAuthorizedUser() {
        return countAuthorizedUser;
    }

    public void setCountAuthorizedUser(long countAuthorizedUser) {
        this.countAuthorizedUser = countAuthorizedUser;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
