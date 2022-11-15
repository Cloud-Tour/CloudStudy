package com.zkd.cloudStudy.vod.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zkd.cloudStudy.model.vod.VideoVisitor;
import com.zkd.cloudStudy.vo.vod.VideoVisitorCountVo;

import java.util.List;


public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    //显示统计数据
    List<VideoVisitorCountVo> findCount(Long courseId, String startDate, String endDate);
}
