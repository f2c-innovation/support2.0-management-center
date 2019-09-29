package com.fit2cloud.support.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "用户角色信息")
public class UserRoleOperateDTO {
    @ApiModelProperty(value = "角色信息列表")
    private List<RoleInfo> roleInfoList;

    @ApiModelProperty(value = "用户Id列表")
    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }

    public void setRoleInfoList(List<RoleInfo> roleInfoList) {
        this.roleInfoList = roleInfoList;
    }
}
