package com.zkd.cloudStudy.vod.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.vod.Video;


public interface VideoService extends IService<Video> {

    //根据课程id删除小节
    void removeVideoByCourseId(Long id);

    //删除小节同时删除视频
    void removeVideoById(Long id);
}
