package com.zkd.cloudStudy.vod.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkd.cloudStudy.model.vod.CourseDescription;
import com.zkd.cloudStudy.vod.mapper.CourseDescriptionMapper;
import com.zkd.cloudStudy.vod.service.CourseDescriptionService;
import org.springframework.stereotype.Service;


@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
