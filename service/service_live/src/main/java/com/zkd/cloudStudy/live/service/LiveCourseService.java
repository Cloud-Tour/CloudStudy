package com.zkd.cloudStudy.live.service;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.live.LiveCourse;
import com.zkd.cloudStudy.vo.live.LiveCourseConfigVo;
import com.zkd.cloudStudy.vo.live.LiveCourseFormVo;
import com.zkd.cloudStudy.vo.live.LiveCourseVo;

import java.util.List;
import java.util.Map;


public interface LiveCourseService extends IService<LiveCourse> {

    //直播课程分页查询
    IPage<LiveCourse> selectPage(Page<LiveCourse> pageParam);

    Boolean save(LiveCourseFormVo liveCourseVo);

    void removeLive(Long id);

    //修改
    void updateById(LiveCourseFormVo liveCourseVo);

    //获取
    LiveCourseFormVo getLiveCourseFormVo(Long id);

    //获取配置
    LiveCourseConfigVo getCourseConfig(Long id);

    //修改配置
    void updateConfig(LiveCourseConfigVo liveCourseConfigVo);

    //获取最近的直播
    List<LiveCourseVo> findLatelyList();

    JSONObject getPlayAuth(Long id, Long userId);

    //根据ID查询课程
    Map<String, Object> getInfoById(Long courseId);
}
