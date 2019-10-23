package com.fit2cloud.support.dto;


import com.fit2cloud.support.model.Agent;
import com.fit2cloud.support.model.AgentCompany;

/**
 * create by mgh 2019.10.23
 */
public class AgentCompanyDTO extends AgentCompany {

    private Agent agent;
    private CompanyDTO companyDto;
    private int countLinkman;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public CompanyDTO getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDTO companyDto) {
        this.companyDto = companyDto;
    }

    public int getCountLinkman() {
        return countLinkman;
    }

    public void setCountLinkman(int countLinkman) {
        this.countLinkman = countLinkman;
    }
}
