package com.fit2cloud.support.dto;

import com.fit2cloud.commons.server.base.domain.Role;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Author: chunxing
 * Date: 2018/5/22  下午3:08
 * Description:
 */
public class RoleOperate extends Role implements Serializable {
    private List<String> moduleIdList;

    public List<String> getModuleIdList() {
        return moduleIdList;
    }

    public void setModuleIdList(List<String> moduleIdList) {
        this.moduleIdList = moduleIdList;
    }

    private HashMap<String, List<String>> permissionMap; //key is module,value is permission

    public HashMap<String, List<String>> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(HashMap<String, List<String>> permissionMap) {
        this.permissionMap = permissionMap;
    }
}
