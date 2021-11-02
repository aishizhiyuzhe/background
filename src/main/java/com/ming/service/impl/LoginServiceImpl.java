package com.ming.service.impl;

import com.ming.common.utils.EncryptUtils;
import com.ming.entity.User;
import com.ming.mapper.UserDao;
import com.ming.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserDao userDao;

    public boolean login(User user) {
        String password=user.getPassword();
        password=EncryptUtils.base64Encrypt(password);
        user.setPassword(password);
        User user1=null;
        try{
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
