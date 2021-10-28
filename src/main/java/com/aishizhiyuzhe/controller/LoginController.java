package com.aishizhiyuzhe.controller;

import com.aishizhiyuzhe.entity.User;
import com.aishizhiyuzhe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    LoginService loginService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        String loginName= (String) session.getAttribute("login");
        String password= (String) session.getAttribute("password");
        if (StringUtils.isEmpty(loginName)){
            return "login";
        }
        User user=new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        if (! loginService.login(user))
            return "login";
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("loginName") String loginName,String password){
        User user=new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        if (! loginService.login(user))
            return "login";
        return "index";
    }

}
