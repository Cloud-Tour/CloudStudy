package com.zkd.cloudStudy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.user.UserInfo;


public interface UserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoOpenid(String openId);
}
