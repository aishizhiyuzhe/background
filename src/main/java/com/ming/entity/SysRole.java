package com.ming.entity;

import java.io.Serializable;

/**
 * sys_role
 * @author 
 */
public class SysRole implements Serializable {
    /**
     * 权限
     */
    private String roleId;

    /**
     * 权限描述
     */
    private String roleName;

    private static final long serialVersionUID = 1L;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}