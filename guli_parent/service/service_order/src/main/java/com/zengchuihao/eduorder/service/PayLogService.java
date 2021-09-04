package com.zengchuihao.eduorder.service;

import com.zengchuihao.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
public interface PayLogService extends IService<PayLog> {

    Map createPayLog(String orderId);

    Map<String, String> queryPayState(String orderNo);

    void updateOrderState(Map<String, String> map);
}
