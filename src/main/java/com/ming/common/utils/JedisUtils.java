package com.ming.common.utils;

import com.mysql.jdbc.StringUtils;
import jdk.nashorn.internal.objects.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.expression.Lists;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/20
 * @Modified By:
 */
public class JedisUtils {
    private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

    private static JedisPool jedisPool=SpringContextHolder.getBean(JedisPool.class);
    //应该配置到配置文件中
    public static final String KEY_PREFIX = "redis.keyPrefix";

    public static void clone(Jedis jedis){
        jedisPool.returnResource(jedis);
    }

    public static void hset(String key,String field,String value){

        Jedis jedis = null;
        try {
            jedis=getResource();
            jedis.hset(key,field,value);
            logger.debug("hset {} = {}", key, value);
        } catch (Exception e) {
            logger.debug("hset {} = {}", key, e);
        } finally {
            clone(jedis);
        }


    }

    public static String hget(String key,String field){
        Jedis jedis=getResource();
        String value=jedis.hget(key,field);
        clone(jedis);
        return value;
    }

    public static Jedis getResource(){
        Jedis jedis=jedisPool.getResource();
        return jedis;
    }

    /**
     * 获取byte[]类型Key
     * @param key
     * @return
     */
    public static byte[] getBytesKey(Object object){
        if(object instanceof String){
            return ((String) object).getBytes();
        }else{
            return ObjectUtils.serialize(object);
        }
    }
}
