package com.fit2cloud.support.dto;

import com.fit2cloud.commons.server.base.domain.Company;
import io.swagger.annotations.ApiModelProperty;

public class CompanyDTO extends Company {

    @ApiModelProperty("部门数量")
    private long countDept;
    @ApiModelProperty("公司数量")
    private long countCompanyAdmin;

    public long getCountDept() {
        return countDept;
    }

    public void setCountDept(long countDept) {
        this.countDept = countDept;
    }

    public long getCountCompanyAdmin() {
        return countCompanyAdmin;
    }

    public void setCountCompanyAdmin(long countCompanyAdmin) {
        this.countCompanyAdmin = countCompanyAdmin;
    }
}
