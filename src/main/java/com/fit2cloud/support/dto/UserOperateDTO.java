package com.fit2cloud.support.dto;

import com.fit2cloud.commons.server.base.domain.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserOperateDTO extends User {

    @ApiModelProperty(value = "角色信息列表")
    private List<RoleInfo> roleInfoList;

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }

    public void setRoleInfoList(List<RoleInfo> roleInfoList) {
        this.roleInfoList = roleInfoList;
    }
}
