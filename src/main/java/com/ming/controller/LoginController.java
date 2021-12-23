package com.ming.controller;

import com.alibaba.fastjson.JSONObject;
import com.ming.common.utils.EncryptUtils;
import com.ming.config.Principal;
import com.ming.entity.CommonResult;
import com.ming.entity.SysUser;
import com.ming.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseController {

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
//        if (! loginService.login(user))
//            return "login";
        return "redirect:"+adminPath;
    }

    @PostMapping("${adminPath}/login")
    public String login(){
        try{
            //这个是shiro框架中的，可以直接获取用户对象
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return "redirect:"+adminPath;
            }
        }catch (UnavailableSecurityManagerException e) {
            return "dist/index";
        }catch (InvalidSessionException e){
            return "dist/index";
        }
        return "dist/index";

    }

    @RequestMapping("${adminPath}")
    public String index(){
        try{
            //这个是shiro框架中的，可以直接获取用户对象
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal)subject.getPrincipal();
            if (principal != null){
                return "dist/index";
            }
        }catch (UnavailableSecurityManagerException e) {
            return "dist/index";
        }catch (InvalidSessionException e){
            return "dist/index";
        }
        return "dist/index";
    }
}
