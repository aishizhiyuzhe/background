package com.ming.mapper;

import com.ming.entity.SysLog;

public interface SysLogDao {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);
}