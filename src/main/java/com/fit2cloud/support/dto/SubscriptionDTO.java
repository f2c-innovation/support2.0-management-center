package com.fit2cloud.support.dto;

import com.fit2cloud.support.model.*;

public class SubscriptionDTO extends Subscription{

    /**
     * 订阅的产品
     */
    private Product product;

    /**
     * 订阅的公司
     */
    private Company company;

    /**
     * 技术支持
     */
    private User supportUser;

    /**
     * 购买用户
     */
    private User buyUser;

    /**
     * 订阅产品的服务等级
     */
    private GradeService gradeService;

    /**
     * 是否过期
     */
    private boolean expireFlag;
    /**
     * 代理对象
     */
    private Agent agent;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getSupportUser() {
        return supportUser;
    }

    public void setSupportUser(User supportUser) {
        this.supportUser = supportUser;
    }

    public User getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(User buyUser) {
        this.buyUser = buyUser;
    }

    public GradeService getGradeService() {
        return gradeService;
    }

    public void setGradeService(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    public boolean isExpireFlag() {
        return expireFlag;
    }

    public void setExpireFlag(boolean expireFlag) {
        this.expireFlag = expireFlag;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
