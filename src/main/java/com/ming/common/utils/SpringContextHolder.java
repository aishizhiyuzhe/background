package com.ming.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.xml.ws.Dispatch;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/11/10
 * @Modified By:
 */
@Service
@Lazy(value = false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext=null;
    private static Logger logger= LoggerFactory.getLogger(SpringContextHolder.class);

    public ApplicationContext getApplicationContext(){
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType){
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(String beanName){
        assertContextInjected();
        return (T) applicationContext.getBean(beanName);
    }

    @Override
    public void destroy() throws Exception {
        if (logger.isDebugEnabled()){
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }
        applicationContext=null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    private static void assertContextInjected(){
        Validate.validState(applicationContext!=null,"applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }
}
