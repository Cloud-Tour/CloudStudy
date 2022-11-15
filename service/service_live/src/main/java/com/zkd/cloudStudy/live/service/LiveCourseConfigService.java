package com.zkd.cloudStudy.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.live.LiveCourseConfig;


public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    //查看配置信息
    LiveCourseConfig getByLiveCourseId(Long id);
}
