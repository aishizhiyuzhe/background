package com.ming.config;

import com.ming.common.utils.EncryptUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/13
 * @Modified By:
 */
public class SystemCredentialsMatcher implements CredentialsMatcher {

    private static final Logger log = LoggerFactory.getLogger(SimpleCredentialsMatcher.class);
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) token;
        SimpleAuthenticationInfo simpleAuthenticationInfo= (SimpleAuthenticationInfo) info;
        String passwordToken= String.valueOf(usernamePasswordToken.getPassword());
//        SimplePrincipalCollection collection= (SimplePrincipalCollection) simpleAuthenticationInfo.getPrincipals();
//        Principal principal=collection.getPrincipalsLazy("")
        String passwordInfo= (String) info.getCredentials();
        try {
            passwordInfo= EncryptUtils.base64Decrypt(passwordInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passwordToken.equals(passwordInfo);
    }

}
