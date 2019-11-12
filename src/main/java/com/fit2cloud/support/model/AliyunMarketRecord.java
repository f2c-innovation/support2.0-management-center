package com.fit2cloud.support.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class AliyunMarketRecord implements Serializable {
    @ApiModelProperty("")
    private String instanceId;

    @ApiModelProperty("客户唯一标识")
    private String aliUid;

    @ApiModelProperty("")
    private String action;

    @ApiModelProperty("云市场业务 ID")
    private String orderBizId;

    @ApiModelProperty("云市场订单 云市场订单 ID")
    private String orderId;

    @ApiModelProperty("商品规格标识，与商品唯一对应，可在商品管理 的销售信息中查看。")
    private String skuId;

    @ApiModelProperty("过期时间(yyyy-MM-dd HH:mm:ss)")
    private String expiredOn;

    @ApiModelProperty("帐号数量，适用于以帐号数量形式售卖的商品")
    private Integer accountQuantity;

    @ApiModelProperty("模板 ID，适用于模板类建站商品")
    private String template;

    @ApiModelProperty("钉钉企业 ID，适用于钉钉类商品")
    private Integer corpId;

    @ApiModelProperty("")
    private String email;

    @ApiModelProperty("")
    private String mobile;

    @ApiModelProperty("以逗号隔开的域名字符串")
    private String domains;

    @ApiModelProperty("阿里云订阅创建时间")
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table aliyun_market_record
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.instance_id
     *
     * @return the value of aliyun_market_record.instance_id
     *
     * @mbg.generated
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.instance_id
     *
     * @param instanceId the value for aliyun_market_record.instance_id
     *
     * @mbg.generated
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId == null ? null : instanceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.ali_uid
     *
     * @return the value of aliyun_market_record.ali_uid
     *
     * @mbg.generated
     */
    public String getAliUid() {
        return aliUid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.ali_uid
     *
     * @param aliUid the value for aliyun_market_record.ali_uid
     *
     * @mbg.generated
     */
    public void setAliUid(String aliUid) {
        this.aliUid = aliUid == null ? null : aliUid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.action
     *
     * @return the value of aliyun_market_record.action
     *
     * @mbg.generated
     */
    public String getAction() {
        return action;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.action
     *
     * @param action the value for aliyun_market_record.action
     *
     * @mbg.generated
     */
    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.order_biz_id
     *
     * @return the value of aliyun_market_record.order_biz_id
     *
     * @mbg.generated
     */
    public String getOrderBizId() {
        return orderBizId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.order_biz_id
     *
     * @param orderBizId the value for aliyun_market_record.order_biz_id
     *
     * @mbg.generated
     */
    public void setOrderBizId(String orderBizId) {
        this.orderBizId = orderBizId == null ? null : orderBizId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.order_id
     *
     * @return the value of aliyun_market_record.order_id
     *
     * @mbg.generated
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.order_id
     *
     * @param orderId the value for aliyun_market_record.order_id
     *
     * @mbg.generated
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.sku_id
     *
     * @return the value of aliyun_market_record.sku_id
     *
     * @mbg.generated
     */
    public String getSkuId() {
        return skuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.sku_id
     *
     * @param skuId the value for aliyun_market_record.sku_id
     *
     * @mbg.generated
     */
    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.expired_on
     *
     * @return the value of aliyun_market_record.expired_on
     *
     * @mbg.generated
     */
    public String getExpiredOn() {
        return expiredOn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.expired_on
     *
     * @param expiredOn the value for aliyun_market_record.expired_on
     *
     * @mbg.generated
     */
    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn == null ? null : expiredOn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.account_quantity
     *
     * @return the value of aliyun_market_record.account_quantity
     *
     * @mbg.generated
     */
    public Integer getAccountQuantity() {
        return accountQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.account_quantity
     *
     * @param accountQuantity the value for aliyun_market_record.account_quantity
     *
     * @mbg.generated
     */
    public void setAccountQuantity(Integer accountQuantity) {
        this.accountQuantity = accountQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.template
     *
     * @return the value of aliyun_market_record.template
     *
     * @mbg.generated
     */
    public String getTemplate() {
        return template;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.template
     *
     * @param template the value for aliyun_market_record.template
     *
     * @mbg.generated
     */
    public void setTemplate(String template) {
        this.template = template == null ? null : template.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.corp_id
     *
     * @return the value of aliyun_market_record.corp_id
     *
     * @mbg.generated
     */
    public Integer getCorpId() {
        return corpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.corp_id
     *
     * @param corpId the value for aliyun_market_record.corp_id
     *
     * @mbg.generated
     */
    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.email
     *
     * @return the value of aliyun_market_record.email
     *
     * @mbg.generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.email
     *
     * @param email the value for aliyun_market_record.email
     *
     * @mbg.generated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.mobile
     *
     * @return the value of aliyun_market_record.mobile
     *
     * @mbg.generated
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.mobile
     *
     * @param mobile the value for aliyun_market_record.mobile
     *
     * @mbg.generated
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.domains
     *
     * @return the value of aliyun_market_record.domains
     *
     * @mbg.generated
     */
    public String getDomains() {
        return domains;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.domains
     *
     * @param domains the value for aliyun_market_record.domains
     *
     * @mbg.generated
     */
    public void setDomains(String domains) {
        this.domains = domains == null ? null : domains.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column aliyun_market_record.create_time
     *
     * @return the value of aliyun_market_record.create_time
     *
     * @mbg.generated
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column aliyun_market_record.create_time
     *
     * @param createTime the value for aliyun_market_record.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}