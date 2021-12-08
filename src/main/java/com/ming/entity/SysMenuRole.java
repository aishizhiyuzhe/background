package com.ming.entity;

import java.io.Serializable;

/**
 * sys_menu_role
 * @author 
 */
public class SysMenuRole implements Serializable {
    private String menuId;

    private String roleId;

    private static final long serialVersionUID = 1L;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}