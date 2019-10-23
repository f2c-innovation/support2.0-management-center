package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.AgentSubscription;

/**
 * create by mgh 2019.10.23
 */
public class AgentSubscriptionDTO extends AgentSubscription {

    private SubscriptionDTO subscriptionDTO;

    public SubscriptionDTO getSubscriptionDTO() {
        return subscriptionDTO;
    }

    public void setSubscriptionDTO(SubscriptionDTO subscriptionDTO) {
        this.subscriptionDTO = subscriptionDTO;
    }
}
