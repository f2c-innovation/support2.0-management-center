package com.fit2cloud.support.dto.request;

import com.fit2cloud.support.dto.RoleInfo;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateUserRequest extends CreateUserBaseRequest{

    @ApiModelProperty(value = "用户来源", hidden = true)
    private String source;

    @ApiModelProperty(value = "角色信息列表", hidden = true)
    private List<RoleInfo> roleInfoList;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<RoleInfo> getRoleInfoList() {
        return roleInfoList;
    }

    public void setRoleInfoList(List<RoleInfo> roleInfoList) {
        this.roleInfoList = roleInfoList;
    }
}
