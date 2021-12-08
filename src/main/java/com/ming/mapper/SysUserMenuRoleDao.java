package com.ming.mapper;

import com.ming.entity.SysUserMenuRole;

public interface SysUserMenuRoleDao {
    int insert(SysUserMenuRole record);

    int insertSelective(SysUserMenuRole record);

    SysUserMenuRole selectLoginName(String loginName);
}