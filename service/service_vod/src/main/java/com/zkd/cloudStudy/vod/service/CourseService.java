package com.zkd.cloudStudy.vod.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.vod.Course;
import com.zkd.cloudStudy.vo.vod.CourseFormVo;
import com.zkd.cloudStudy.vo.vod.CoursePublishVo;
import com.zkd.cloudStudy.vo.vod.CourseQueryVo;

import java.util.List;
import java.util.Map;


public interface CourseService extends IService<Course> {

    //获取分页列表
    Map<String, Object> findPage(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    //添加课程基本信息
    Long saveCourseInfo(CourseFormVo courseFormVo);

    //根据id获取课程信息
    CourseFormVo getCourseFormVoById(Long id);

    //根据id修改课程信息
    void updateCourseById(CourseFormVo courseFormVo);

    //根据id获取课程发布信息
    CoursePublishVo getCoursePublishVo(Long id);

    //根据id发布课程
    boolean publishCourseById(Long id);

    //删除课程
    void removeCourseById(Long id);

    //根据id查询课程
    Map<String, Object> getInfoById(Long courseId);

    List<Course> findlist();
}
