package com.ming.entity;

import java.io.Serializable;

/**
 * sys_user_menu_role
 * @author 
 */
public class SysUserMenuRole implements Serializable {
    private String loginName;

    private String roleId;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}