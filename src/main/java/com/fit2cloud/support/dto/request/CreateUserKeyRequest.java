package com.fit2cloud.support.dto.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by liqiang on 2018/11/22.
 */
public class CreateUserKeyRequest {

    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    @ApiModelProperty("描述")
    private String description;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
