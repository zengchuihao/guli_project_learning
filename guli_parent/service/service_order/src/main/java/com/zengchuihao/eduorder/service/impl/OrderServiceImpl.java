package com.zengchuihao.eduorder.service.impl;

import com.zengchuihao.commonutils.order.CourseWebOrder;
import com.zengchuihao.commonutils.order.UcenterMemberOrder;
import com.zengchuihao.eduorder.client.EduClient;
import com.zengchuihao.eduorder.client.UcenterClient;
import com.zengchuihao.eduorder.entity.Order;
import com.zengchuihao.eduorder.mapper.OrderMapper;
import com.zengchuihao.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengchuihao.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {

        //通过远程调用根据用户id获取用户信息
        System.out.println("==============");
        UcenterMemberOrder member = ucenterClient.getMemberOrderInfo(memberId);
        System.out.println("++++++++++++++++++");

        //通过远程调用根据课程id获取课程信息
        CourseWebOrder courseOrderInfo = eduClient.getCourseOrderInfo(courseId);

        //创建订单
        Order order = new Order();

        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseOrderInfo.getTitle());
        order.setCourseCover(courseOrderInfo.getCover());
        order.setTeacherName(courseOrderInfo.getTeacherName());
        order.setTotalFee(courseOrderInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(member.getMobile());
        order.setNickname(member.getNickname());
        order.setStatus(0); //0未支付，1已支付
        order.setPayType(1);//1微信支付，2支付宝支付
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
