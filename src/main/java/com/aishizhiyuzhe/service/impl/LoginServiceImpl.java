package com.aishizhiyuzhe.service.impl;

import com.aishizhiyuzhe.common.utils.EncryptUtils;
import com.aishizhiyuzhe.entity.User;
import com.aishizhiyuzhe.mapper.UserDao;
import com.aishizhiyuzhe.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserDao userDao;

    public boolean login(User user) {
        String password=user.getPassword();
        password=EncryptUtils.base64Encrypt(password);
        User user1=null;
        try{
            userDao.insert();
             user1=userDao.selectUser(user.getLoginName());
        }catch (Exception ex){
            System.out.println(ex);
        }

        if (null ==user1){
            return false;
        }
        if (password.equals(user1.getPassword()))
            return true;
        return false;
    }
}
