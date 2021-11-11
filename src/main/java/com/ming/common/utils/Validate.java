package com.ming.common.utils;

/**
 * @Author: Ming
 * @Description:У����
 * @Date: Created in 2021/11/10
 * @Modified By:
 */
public class Validate {


    /**
     * ���ڽ����жϣ���Ϊfalse�򱨴�
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
