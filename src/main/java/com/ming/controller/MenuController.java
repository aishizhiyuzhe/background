package com.ming.controller;

import com.alibaba.fastjson.JSONObject;
import com.ming.entity.CommonResult;
import com.ming.entity.SysMenu;
import com.ming.entity.SysUser;
import com.ming.service.SystemService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Ming
 * @Description:菜单控制
 * @Date: Created in 2021/11/24
 * @Modified By:
 */
@Controller
public class MenuController extends BaseController{

    @Resource
    SystemService systemService;

    @RequestMapping(value = "${adminPath}/sys/menus",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getSysMenus(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        SysUser user= JSONObject.parseObject((String) session.getAttribute("user"),SysUser.class);
        List<SysMenu> allMenu = systemService.findAllMenu(user);
        long code=200;
        String message="查询成功";
        if (allMenu.size()==0){
            code=400;
            message="查询目录失败,未登录或没有权限";
        }
        CommonResult<List<SysMenu>> commonResult=new CommonResult<List<SysMenu>>(message,code,allMenu);
        return JSONObject.toJSONString(commonResult);
    }
}
