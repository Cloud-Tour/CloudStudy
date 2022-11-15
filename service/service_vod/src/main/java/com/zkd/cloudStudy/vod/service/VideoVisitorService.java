package com.zkd.cloudStudy.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.vod.VideoVisitor;

import java.util.Map;


public interface VideoVisitorService extends IService<VideoVisitor> {

    //显示统计数据
    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
