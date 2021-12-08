package com.ming.mapper;

import com.ming.entity.SysMenu;

import java.util.List;

public interface SysMenuDao {
    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectAll();
}