package com.aishizhiyuzhe.controller;

import com.aishizhiyuzhe.entity.User;
import com.aishizhiyuzhe.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    LoginService loginService;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, Model model){
        HttpSession session=request.getSession();
        String loginName= (String) session.getAttribute("login");
        String password= (String) session.getAttribute("password");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
//        if (StringUtils.isEmpty(loginName)){
////            return (ModelAndView) model;
//        }
//        User user=new User();
//        user.setLoginName(loginName);
//        user.setPassword(password);
//        if (! loginService.login(user))
//            return "login";
//        return "index";
    }

}
