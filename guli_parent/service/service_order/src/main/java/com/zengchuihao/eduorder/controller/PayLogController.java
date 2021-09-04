package com.zengchuihao.eduorder.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-22
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //根据订单号生成二维码
    @GetMapping("createPayLog/{orderId}")
    public R createPayLog(@PathVariable String orderId) {
       Map map = payLogService.createPayLog(orderId);
       System.out.println("***********生成的二维码map集合"+map);
       return R.ok().data(map);
    }

    //根据订单号通过微信固定地址查询支付状态,如果支付成功将支付记录添加到paylog表，更新订单的支付状态
    @GetMapping("queryPayState/{orderNo}")
    public R queryPayState(@PathVariable String orderNo) {

        Map<String, String> map = payLogService.queryPayState(orderNo);
        System.out.println("&&&&&&&&&&查询到生成的二维码map集合"+map);
        if (map == null) {
            return R.error().message("支付出错了");
        }

        if (map.get("trade_state").equals("SUCCESS")) { //添加支付记录及更新订单状态
            payLogService.updateOrderState(map);
            return R.ok().message("支付成功");
        }

        return R.ok().code(25000).message("支付中");
    }

}

