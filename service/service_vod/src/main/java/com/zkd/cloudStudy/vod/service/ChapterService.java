package com.zkd.cloudStudy.vod.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.vod.Chapter;
import com.zkd.cloudStudy.vo.vod.ChapterVo;

import java.util.List;


public interface ChapterService extends IService<Chapter> {

    //章节小结列表
    List<ChapterVo> getNestedTreeList(Long courseId);

    //根据课程id删除章节
    void removeChapterByCourseId(Long id);
}
