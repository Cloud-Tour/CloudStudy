package com.zkd.cloudStudy.activity.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.activity.CouponInfo;
import com.zkd.cloudStudy.model.activity.CouponUse;
import com.zkd.cloudStudy.vo.activity.CouponUseQueryVo;


public interface CouponInfoService extends IService<CouponInfo> {

    //获取已经使用优惠券列表（条件查询分页）
    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);

    //更新优惠券使用状态
    void updateCouponInfoUseStatus(Long couponUseId, Long orderId);
}
