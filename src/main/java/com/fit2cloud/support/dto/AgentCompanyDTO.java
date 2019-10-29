package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.Agent;
import com.fit2cloud.support.model.AgentCompany;

public class AgentCompanyDTO extends AgentCompany {

    private Agent agent;
    private CompanyDTO companyDTO;
    private int countLinkman;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    public int getCountLinkman() {
        return countLinkman;
    }

    public void setCountLinkman(int countLinkman) {
        this.countLinkman = countLinkman;
    }
}
