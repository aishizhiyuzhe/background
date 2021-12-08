package com.ming.service;

import com.ming.entity.SysMenu;
import com.ming.entity.SysUser;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.io.IOException;
import java.util.List;

/**
 * 系统功能模块
 */
public interface SystemService {

    /**
     * 获取当前角色下所有菜单
     * @param user
     * @return
     */
    List<SysMenu> findMenu(SysUser user) throws IOException;

    List<SysMenu> findAllMenu(SysUser user) throws IOException;

}
