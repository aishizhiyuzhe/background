package com.ming.controller;

import org.springframework.beans.factory.annotation.Value;

public class BaseController {

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;
}
