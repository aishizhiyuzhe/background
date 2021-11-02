package com.ming.entity;

public class CommonResult<T> {
    private String message;
    private Long code;
    private T data;
    public CommonResult(Long code,String message){
        this(message,code,null);
    }

    public CommonResult(String message, Long code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
