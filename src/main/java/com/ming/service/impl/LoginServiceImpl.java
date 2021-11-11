package com.ming.service.impl;

import com.ming.common.utils.EncryptUtils;
import com.ming.entity.SysUser;
import com.ming.mapper.SysUserDao;
import com.ming.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    SysUserDao sysUserDao;

    public boolean login(SysUser user) {
        String password=user.getPassword();
        password=EncryptUtils.base64Encrypt(password);
        user.setPassword(password);
        SysUser user1=null;
        try{
             user1=sysUserDao.selectUser(user.getLoginName());
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
