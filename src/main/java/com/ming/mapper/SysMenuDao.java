package com.ming.mapper;

import com.ming.entity.SysMenu;

public interface SysMenuDao {
    int insert(SysMenu record);

    int insertSelective(SysMenu record);
}