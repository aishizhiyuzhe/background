package com.ming.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: Ming
 * @Description:用于存放静态配置（configuration.properties）
 * @Date: Created in 2021/11/18
 * @Modified By:
 */
public class ConfigUtils {
    //保存全局配置
    private static Map<String,String> configCache=new HashMap<>();

    private static String loader = "configuration.properties";

    public static String getValue(String key) throws IOException {
        if (configCache.size()==0){
            Properties properties=new Properties();
            InputStream in=ConfigUtils.class.getClassLoader().getResourceAsStream(loader);
            properties.load(in);
            configCache.put(key, (String) properties.get(key));
        }
        return configCache.get(key);
    }

}
