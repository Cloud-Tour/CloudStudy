package com.zkd.cloudStudy.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkd.cloudStudy.model.order.OrderDetail;
import com.zkd.cloudStudy.order.mapper.OrderDetailMapper;
import com.zkd.cloudStudy.order.service.OrderDetailService;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
