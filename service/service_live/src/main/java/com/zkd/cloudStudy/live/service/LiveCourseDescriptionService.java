package com.zkd.cloudStudy.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.live.LiveCourseDescription;


public interface LiveCourseDescriptionService extends IService<LiveCourseDescription> {

    LiveCourseDescription getLiveCourseById(Long id);
}
