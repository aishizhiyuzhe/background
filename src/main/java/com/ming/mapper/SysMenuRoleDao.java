package com.ming.mapper;

import com.ming.entity.SysMenu;
import com.ming.entity.SysMenuRole;

import java.util.List;

public interface SysMenuRoleDao {
    int insert(SysMenuRole record);

    int insertSelective(SysMenuRole record);

    List<SysMenuRole> selectMenuId(String menuId);
}