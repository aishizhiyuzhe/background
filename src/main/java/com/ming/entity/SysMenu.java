package com.ming.entity;

import java.io.Serializable;
import java.util.List;

/**
 * sys_menu
 * @author 
 */
public class SysMenu implements Serializable {
    /**
     * 当前菜单ID
     */
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单Id
     */
    private String parentId;

    /**
     * 路径
     */
    private String menuUrl;

    /**
     * 图片URL
     */
    private String imgUrl;

    /**
     * 是否启用
     */
    private String enable;

    private String weight;

    private List<SysMenuRole> sysMenuRoles;

    private static final long serialVersionUID = 1L;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<SysMenuRole> getSysMenuRoles() {
        return sysMenuRoles;
    }

    public void setSysMenuRoles(List<SysMenuRole> sysMenuRoles) {
        this.sysMenuRoles = sysMenuRoles;
    }
}