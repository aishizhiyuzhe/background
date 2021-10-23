package com.aishizhiyuzhe.mapper;

import com.aishizhiyuzhe.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    public User selectUser(String loginName);

}
