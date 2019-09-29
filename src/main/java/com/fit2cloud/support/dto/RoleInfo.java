package com.fit2cloud.support.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


/**
 * @Author caixiaoqiu1987
 * @Date 2019/9/26 6:20 PM
 * @Version 1.0
 **/
public class RoleInfo implements Serializable {

    @ApiModelProperty("是否创建新的工作空间")
    private boolean workspace;//前缀不能is、create!
    @ApiModelProperty("组织 ID 集合")
    private List<String> organizationIds;
    @ApiModelProperty("普通用户选择工作空间为哪一个组织")
    private String selectOrganizationId;//普通用户选择工作空间为哪一个组织
    @ApiModelProperty("工作空间 ID 集合")
    private List<String> workspaceIds;
    @ApiModelProperty("角色ID")
    private String roleId;

    public boolean getWorkspace() {
        return workspace;
    }

    public void setWorkspace(boolean workspace) {
        this.workspace = workspace;
    }

    public List<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(List<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public List<String> getWorkspaceIds() {
        return workspaceIds;
    }

    public void setWorkspaceIds(List<String> workspaceIds) {
        this.workspaceIds = workspaceIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSelectOrganizationId() {
        return selectOrganizationId;
    }

    public void setSelectOrganizationId(String selectOrganizationId) {
        this.selectOrganizationId = selectOrganizationId;
    }
}
