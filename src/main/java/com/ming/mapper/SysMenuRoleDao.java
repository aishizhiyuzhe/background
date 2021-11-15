package com.ming.mapper;

import com.ming.entity.SysMenuRole;

public interface SysMenuRoleDao {
    int insert(SysMenuRole record);

    int insertSelective(SysMenuRole record);
}