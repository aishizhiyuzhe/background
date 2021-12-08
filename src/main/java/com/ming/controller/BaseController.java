package com.ming.controller;

import org.springframework.beans.factory.annotation.Value;

public class BaseController {


    @Value("${adminPath}")
    protected String adminPath;
}
