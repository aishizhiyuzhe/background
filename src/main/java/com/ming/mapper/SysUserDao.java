package com.ming.mapper;

import com.ming.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserDao {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    public SysUser selectUser(@Param("loginName")String loginName);
}