package com.fit2cloud.support.dto;

import com.fit2cloud.commons.server.base.domain.Workspace;
import io.swagger.annotations.ApiModelProperty;

public class WorkspaceDTO extends Workspace {

    @ApiModelProperty("授权用户数量")
    private long countAuthorizedUser;
    @ApiModelProperty("组织名称")
    private String organizationName;

    public long getCountAuthorizedUser() {
        return countAuthorizedUser;
    }

    public void setCountAuthorizedUser(long countAuthorizedUser) {
        this.countAuthorizedUser = countAuthorizedUser;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
