package com.zengchuihao.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.commonutils.JwtUtils;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.eduorder.entity.Order;
import com.zengchuihao.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //根据课程id和用户id创建订单,返回订单id
    @PostMapping("createOrder/{courseId}")
    public R saveOrder (@PathVariable String courseId, HttpServletRequest request) {

        String orderId = orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));

        return R.ok().data("orderId", orderId);
    }


    //根据订单号得到订单的对象信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderId);
        Order order = orderService.getOne(queryWrapper);
        return R.ok().data("item", order);
    }

    //根据用户id和课程id查询课程是否购买state
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);

        if (count>0) {
            return true;
        } else {
            return false;
        }
    }


}

