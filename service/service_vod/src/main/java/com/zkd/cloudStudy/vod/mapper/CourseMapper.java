package com.zkd.cloudStudy.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkd.cloudStudy.model.vod.Course;
import com.zkd.cloudStudy.vo.vod.CoursePublishVo;
import com.zkd.cloudStudy.vo.vod.CourseVo;
import org.apache.ibatis.annotations.Mapper;


public interface CourseMapper extends BaseMapper<Course> {

    //根据id获取课程发布信息
    CoursePublishVo selectCoursePublishVoById(Long id);

    CourseVo selectCourseVoById(Long courseId);
}
