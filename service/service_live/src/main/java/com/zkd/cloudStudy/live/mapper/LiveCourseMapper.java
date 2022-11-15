package com.zkd.cloudStudy.live.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkd.cloudStudy.model.live.LiveCourse;
import com.zkd.cloudStudy.vo.live.LiveCourseVo;

import java.util.List;


public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    //获取最近直播
    List<LiveCourseVo> findLatelyList();
}
