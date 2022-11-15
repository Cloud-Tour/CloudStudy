package com.zkd.cloudStudy.order.service;

import java.util.Map;

public interface WXPayService {
    //微信支付
    Map createJsapi(String orderNo);

    //根据订单号调用微信接口查询支付状态
    Map<String, String> queryPayStatus(String orderNo);
}
