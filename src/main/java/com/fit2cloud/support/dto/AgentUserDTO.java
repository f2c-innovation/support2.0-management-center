package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.Agent;
import com.fit2cloud.support.model.AgentUser;
import com.fit2cloud.support.model.User;

/**
 * create by mgh 2019.10.23
 */
public class AgentUserDTO extends AgentUser {

    private Agent agent;

    private User user;

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
