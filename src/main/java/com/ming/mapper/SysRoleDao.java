package com.ming.mapper;

import com.ming.entity.SysRole;

public interface SysRoleDao {
    int insert(SysRole record);

    int insertSelective(SysRole record);
}