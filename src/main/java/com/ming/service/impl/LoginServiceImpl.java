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

    public SysUser  login(String userName) {
//        String password=user.getPassword();
//        password=EncryptUtils.base64Encrypt(password);
//        user.setPassword(password);
        return sysUserDao.selectUser(userName);

    }
}
