package com.fit2cloud.support.dto.vo;


import com.fit2cloud.commons.server.base.domain.User;

public class UserAddVo extends User {

    private Boolean newGroup;
    private String orgId;
    private String roleId;
    private String orgName;

    public Boolean getNewGroup() {
        return newGroup;
    }

    public void setNewGroup(Boolean newGroup) {
        this.newGroup = newGroup;
    }

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
