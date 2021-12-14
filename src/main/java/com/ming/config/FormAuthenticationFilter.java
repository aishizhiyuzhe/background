package com.ming.config;

import com.alibaba.druid.util.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/13
 * @Modified By:
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String userName= getUsername(request);
        String password= getPassword(request);
        String host=getHost(request);
        //String rememberMeParam=getRememberMeParam();
        return new UsernamePasswordToken(userName,password, false,host);
    }
    /**
     * 获取登录用户名
     */
    protected String getUsername(ServletRequest request) {
        String username = super.getUsername(request);
        if (StringUtils.isEmpty(username)){
            username = !StringUtils.isEmpty((String) request.getAttribute(getUsernameParam()))?(String) request.getAttribute(getUsernameParam()):"";
        }
        return username;
    }

    /**
     * 获取登录密码
     * 不太清楚为什么要走那么多步
     */
    @Override
    protected String getPassword(ServletRequest request) {
        String password = super.getPassword(request);
        if (StringUtils.isEmpty(password)){
            password =!StringUtils.isEmpty((String) request.getAttribute(getPasswordParam()))?(String) request.getAttribute(getPasswordParam()):"";
        }
        return password;
    }

}
