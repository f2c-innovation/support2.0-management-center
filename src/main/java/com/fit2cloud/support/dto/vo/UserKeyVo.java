package com.fit2cloud.support.dto.vo;


import com.fit2cloud.commons.server.base.domain.UserKey;

public class UserKeyVo extends UserKey {

    private String orgId;
    private String roleId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
