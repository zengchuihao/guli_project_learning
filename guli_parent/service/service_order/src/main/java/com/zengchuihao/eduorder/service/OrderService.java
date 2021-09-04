package com.zengchuihao.eduorder.service;

import com.zengchuihao.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
