package com.ming.controller;

import com.alibaba.fastjson.JSONObject;
import com.ming.common.utils.EncryptUtils;
import com.ming.entity.CommonResult;
import com.ming.entity.SysUser;
import com.ming.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("${adminPath}/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        SysUser user= JSONObject.parseObject((String) session.getAttribute("user"),SysUser.class);
        if (null==user){
            return "login";
        }
        try {
            user.setPassword(EncryptUtils.base64Decrypt(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (! loginService.login(user))
            return "login";
        return "index";
    }

    @PostMapping("${adminPath}/login")
    public String login(@RequestParam("loginName") String loginName, String password, Model model,HttpServletRequest request){
        SysUser user=new SysUser();
        user.setLoginName(loginName);
        user.setPassword(password);
        if (! loginService.login(user)){
            CommonResult commonResult=new CommonResult(200L,"用户名或密码错误");
            model.addAttribute("result",commonResult);
            return "login";
        }
        HttpSession session=request.getSession();
        session.setAttribute("user", JSONObject.toJSONString(user));
        return "index";
    }

    @RequestMapping("${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response){
        HttpSession session=request.getSession();
        String loginName= (String) session.getAttribute("login");
        String password= (String) session.getAttribute("password");
        if (StringUtils.isEmpty(loginName)){
            return "login";
        }
        SysUser user=new SysUser();
        user.setLoginName(loginName);
        user.setPassword(password);
        if (! loginService.login(user))
            return "login";
        return "index";
    }
}
