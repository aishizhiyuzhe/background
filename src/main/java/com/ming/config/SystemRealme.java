package com.ming.config;

import com.alibaba.druid.util.StringUtils;
import com.ming.entity.SysUser;
import com.ming.service.LoginService;
import com.ming.service.SystemService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/13
 * @Modified By:
 */
public class SystemRealme extends AuthorizingRealm {

    @Resource
    private LoginService loginService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token1= (UsernamePasswordToken) token;
        String userName=token1.getUsername();
        String password= String.valueOf(token1.getPassword());
        if (StringUtils.isEmpty(userName)){
            return null;
        }
        SysUser sysUser=loginService.login(userName);
        if (sysUser==null){
            return null;
        }
        Principal principal=new Principal(userName,password);
        return new SimpleAuthenticationInfo(principal,sysUser.getPassword(),this.getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        SystemCredentialsMatcher systemCredentialsMatcher=new SystemCredentialsMatcher();
        super.setCredentialsMatcher(systemCredentialsMatcher);
    }
}
