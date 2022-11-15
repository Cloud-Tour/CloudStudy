package com.zkd.cloudStudy.order.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zkd.cloudStudy.model.order.OrderInfo;
import com.zkd.cloudStudy.vo.order.OrderFormVo;
import com.zkd.cloudStudy.vo.order.OrderInfoQueryVo;
import com.zkd.cloudStudy.vo.order.OrderInfoVo;

import java.util.Map;


public interface OrderInfoService extends IService<OrderInfo> {

    //获取订单列表
    Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    //生成点播课程订单
    Long submitOrder(OrderFormVo orderFormVo);


    OrderInfoVo getOrderInfoVoById(Long id);

    //更新订单状态
    void updateOrderStatus(String out_trade_no);
}
