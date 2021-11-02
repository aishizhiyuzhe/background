package com.ming.mapper;

import com.ming.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public User selectUser(@Param("loginName")String loginName);

    public void insert();
}
