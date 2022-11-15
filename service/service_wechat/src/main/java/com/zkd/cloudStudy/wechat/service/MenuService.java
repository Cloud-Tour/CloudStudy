package com.zkd.cloudStudy.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.wechat.Menu;
import com.zkd.cloudStudy.vo.wechat.MenuVo;

import java.util.List;


public interface MenuService extends IService<Menu> {

    //获取所有菜单，按照一级和二级菜单封装
    List<MenuVo> findMenuInfo();

    //获取所有一级菜单
    List<Menu> findMenuOneInfo();

    void syncMenu();

    void removeMenu();
}
