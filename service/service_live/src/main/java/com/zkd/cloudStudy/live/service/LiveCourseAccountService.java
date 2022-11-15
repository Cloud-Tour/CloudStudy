package com.zkd.cloudStudy.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.live.LiveCourseAccount;


public interface LiveCourseAccountService extends IService<LiveCourseAccount> {

    LiveCourseAccount getByLiveCourseId(Long liveCourseId);
}
