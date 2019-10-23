package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.Agent;
import com.fit2cloud.support.model.Company;

import java.util.ArrayList;
import java.util.List;

/**
 * create by mgh 2019.10.23
 */
public class CompanyDTO extends Company {

    private List<String> agentIdList = new ArrayList<>();

    private long countCompanyAdmin;

    private long countDept;

    public List<String> getAgentIdList() {
        return agentIdList;
    }

    public void setAgentIdList(List<String> agentIdList) {
        this.agentIdList = agentIdList;
    }

    public long getCountCompanyAdmin() {
        return countCompanyAdmin;
    }

    public void setCountCompanyAdmin(long countCompanyAdmin) {
        this.countCompanyAdmin = countCompanyAdmin;
    }

    public long getCountDept() {
        return countDept;
    }

    public void setCountDept(long countDept) {
        this.countDept = countDept;
    }
}
