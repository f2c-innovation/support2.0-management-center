package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * create by mgh 2019.10.23
 */
public class AgentDTO extends Agent {

    private List<AgentCompanyDTO> agentCompanyDtoList;

    private List<AgentUserDTO> agentUserDtoList;

    private List<AgentSubscriptionDTO> agentSubscriptionDtoList;

    private List<String> companyIdList = new ArrayList<>();

    private List<String> userIdList = new ArrayList<>();

    private List<String> subscriptionIdList = new ArrayList<>();

    /**
     * 代理订阅的数量
     */
    private int subscriptionCount;

    /**
     * 代理客户的数量
     */
    private int companyCount;

    /**
     * 代理用户的数量
     */
    private int userCount;

    public List<AgentCompanyDTO> getAgentCompanyDtoList() {
        return agentCompanyDtoList;
    }

    public void setAgentCompanyDtoList(List<AgentCompanyDTO> agentCompanyDtoList) {
        this.agentCompanyDtoList = agentCompanyDtoList;
    }

    public List<AgentUserDTO> getAgentUserDtoList() {
        return agentUserDtoList;
    }

    public void setAgentUserDtoList(List<AgentUserDTO> agentUserDtoList) {
        this.agentUserDtoList = agentUserDtoList;
    }

    public List<AgentSubscriptionDTO> getAgentSubscriptionDtoList() {
        return agentSubscriptionDtoList;
    }

    public void setAgentSubscriptionDtoList(List<AgentSubscriptionDTO> agentSubscriptionDtoList) {
        this.agentSubscriptionDtoList = agentSubscriptionDtoList;
    }

    public List<String> getCompanyIdList() {
        return companyIdList;
    }

    public void setCompanyIdList(List<String> companyIdList) {
        this.companyIdList = companyIdList;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<String> getSubscriptionIdList() {
        return subscriptionIdList;
    }

    public void setSubscriptionIdList(List<String> subscriptionIdList) {
        this.subscriptionIdList = subscriptionIdList;
    }

    public int getSubscriptionCount() {
        return subscriptionCount;
    }

    public void setSubscriptionCount(int subscriptionCount) {
        this.subscriptionCount = subscriptionCount;
    }

    public int getCompanyCount() {
        return companyCount;
    }

    public void setCompanyCount(int companyCount) {
        this.companyCount = companyCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
