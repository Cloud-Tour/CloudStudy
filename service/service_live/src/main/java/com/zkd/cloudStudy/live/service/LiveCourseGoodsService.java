package com.zkd.cloudStudy.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.live.LiveCourseGoods;

import java.util.List;


public interface LiveCourseGoodsService extends IService<LiveCourseGoods> {
    //获取课程商品列表
    List<LiveCourseGoods> findByLiveCourseId(Long id);
}
