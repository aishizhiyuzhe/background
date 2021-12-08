package com.ming.service.impl;

import com.ming.common.utils.ConfigUtils;
import com.ming.entity.SysMenu;
import com.ming.entity.SysMenuRole;
import com.ming.entity.SysUser;
import com.ming.entity.SysUserMenuRole;
import com.ming.mapper.SysMenuDao;
import com.ming.mapper.SysUserMenuRoleDao;
import com.ming.service.SystemService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/11/23
 * @Modified By:
 */
@Service
public class SystemServiceImpl  implements SystemService {

    @Resource
    SysMenuDao sysMenuDao;
    @Resource
    SysUserMenuRoleDao sysUserMenuRoleDao;

    @Override
    public List<SysMenu> findMenu(SysUser user) throws IOException {
        String cache = ConfigUtils.getValue("isOpenCache");
        if (!StringUtils.isEmpty(cache) && cache.equals("true")){

        }else {
            return findAllMenu(user);
        }
        return null;
    }

    @Override
    public List<SysMenu> findAllMenu(SysUser user) throws IOException {
        List<SysMenu> list=new ArrayList<>();
        SysUserMenuRole role=sysUserMenuRoleDao.selectLoginName(user.getLoginName());
        List<SysMenu> sysMenus=sysMenuDao.selectAll();
        for (SysMenu i:sysMenus){
            for (SysMenuRole j:i.getSysMenuRoles()){
                if (j.getRoleId().equals(role.getRoleId())){
                    list.add(i);
                    break;
                }
            }
        }
        return list;
    }
}
