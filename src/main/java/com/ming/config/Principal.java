package com.ming.config;

import java.io.Serializable;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/13
 * @Modified By:
 */
public class Principal implements Serializable {
    String userName;
    String password;


    public Principal(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
