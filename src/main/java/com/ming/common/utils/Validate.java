package com.ming.common.utils;

/**
 * @Author: Ming
 * @Description:校验类
 * @Date: Created in 2021/11/10
 * @Modified By:
 */
public class Validate {


    /**
     * 用于进行判断，若为false则报错
     * @param expression
     * @param message
     * @param values
     */
    public static void validState(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalStateException(String.format(message, values));
        }
    }
}
