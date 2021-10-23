package com.aishizhiyuzhe.service.impl;

import com.aishizhiyuzhe.common.utils.EncryptUtils;
import com.aishizhiyuzhe.entity.User;
import com.aishizhiyuzhe.mapper.UserDao;
import com.aishizhiyuzhe.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

//    @Resource
//    UserDao userDao;

    public boolean login(User user) {
//        String password=user.getPassword();
//        password=EncryptUtils.base64Encrypt(password);
//
//        User user1=userDao.selectUser(user.getLoginName());
//        if (null ==user1){
//            return false;
//        }
//        String password2=EncryptUtils.base64Encrypt(user1.getPassword());
//        if (password.equals(password2))
//            return true;
        return false;
    }
}
