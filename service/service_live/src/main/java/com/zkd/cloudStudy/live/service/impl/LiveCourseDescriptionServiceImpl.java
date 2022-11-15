package com.zkd.cloudStudy.live.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkd.cloudStudy.live.mapper.LiveCourseDescriptionMapper;
import com.zkd.cloudStudy.live.service.LiveCourseDescriptionService;
import com.zkd.cloudStudy.model.live.LiveCourseDescription;
import org.springframework.stereotype.Service;


@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription> implements LiveCourseDescriptionService {
    @Override
    public LiveCourseDescription getLiveCourseById(Long liveCourseId) {
        return this.getOne(new LambdaQueryWrapper<LiveCourseDescription>().eq(LiveCourseDescription::getLiveCourseId, liveCourseId));
    }
}
