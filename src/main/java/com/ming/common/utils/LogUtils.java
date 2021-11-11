package com.ming.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ming.entity.SysLog;
import com.ming.entity.SysUser;
import com.ming.mapper.SysLogDao;
import com.mysql.jdbc.log.Log;
import org.springframework.asm.Handle;
import org.springframework.http.HttpRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class LogUtils {

    @Resource
    private static SysLogDao sysLogDao=SpringContextHolder.getBean("sysLogDao");

    public static void saveLog(HttpServletRequest request,String title){
        saveLog(request,title,null,null);
    }

    public static void saveLog(HttpServletRequest request, String title, Object ex, Object handle){
        SysLog sysLog=new SysLog();
        HttpSession session=request.getSession();
        SysUser user= JSONObject.parseObject((String) session.getAttribute("user"),SysUser.class);

        sysLog.setCreateBy(user.getLoginName());
        sysLog.setCreateDate(new Date(System.currentTimeMillis()));
        if(null!=ex){
            sysLog.setType("1");
            sysLog.setException(ex.toString());
        }else {
            sysLog.setType("2");
        }

        sysLog.setMethod(request.getMethod());
        sysLog.setRequestUri(request.getRequestURI());
        sysLog.setTitle(title);
        sysLog.setParams(handle.toString());
        new SaveLogThread(sysLog,handle).run();
    }

    public static class SaveLogThread extends Thread{

        private SysLog log;
        private Object handler;
        public SaveLogThread(SysLog log,Object handler){
            super(SaveLogThread.class.getSimpleName());
            this.log=log;
            this.handler=handler;
            this.log.setId(String.valueOf(SnowFlake.nextId()));
        }

        @Override
        public void run() {
            sysLogDao.insert(log);
        }

    }


}
