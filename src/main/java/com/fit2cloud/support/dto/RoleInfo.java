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

    @ApiModelProperty("是否创建新的部门")
    private boolean department;//前缀不能is、create!
    @ApiModelProperty("公司 ID 集合")
    private List<String> companyIds;
    @ApiModelProperty("普通用户选择部门为哪一公司")
    private String selectCompanyId;//普通用户选择部门为哪一个公司
    @ApiModelProperty("部门 ID 集合")
    private List<String> deptIds;
    @ApiModelProperty("角色ID")
    private String roleId;


    public boolean getDepartment() {
        return department;
    }

    public void setDepartment(boolean department) {
        this.department = department;
    }

    public List<String> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(List<String> companyIds) {
        this.companyIds = companyIds;
    }

    public String getSelectCompanyId() {
        return selectCompanyId;
    }

    public void setSelectCompanyId(String selectCompanyId) {
        this.selectCompanyId = selectCompanyId;
    }

    public List<String> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<String> deptIds) {
        this.deptIds = deptIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

}
