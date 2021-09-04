package com.zengchuihao.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.zengchuihao.eduorder.entity.Order;
import com.zengchuihao.eduorder.entity.PayLog;
import com.zengchuihao.eduorder.mapper.PayLogMapper;
import com.zengchuihao.eduorder.service.OrderService;
import com.zengchuihao.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengchuihao.eduorder.utils.HttpClient;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createPayLog(String orderNo) {

        try {
            //1、根据订单号查询订信息
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(queryWrapper);

            //2、根据查询订单信息使用map设置生成二维码所需参数
            HashMap<String, String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            //3、给微信的固定地址发送参数为xml的httpclient请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //4、得到请求结果，用map封装并返回
            //将返回的xml格式的结果转换成map格式返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(client.getContent());//client返回结果为xml

            //创建新的map用于封装最终结果
            HashMap<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            return map;
        } catch (Exception e) {
            throw new MyCreateException(20001, "生成微信支付二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayState(String orderNo) {

        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方数据，将xml转为map返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(client.getContent());
            return resultMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyCreateException(20001,"查询不到该订单状态");
        }

    }

    //paylog表插入数据，更新order表状态
    @Override
    public void updateOrderState(Map<String, String> map) {

        //更新order表的支付状态
        String orderNo = map.get("out_trade_no");
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(queryWrapper);

        if (order.getStatus().intValue() == 1) return;

        order.setStatus(1);
        orderService.updateById(order);

        //向支付记录表添加新的记录
        PayLog payLog=new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);//插入到支付日志表

    }
}
