package com.zengchuihao.staService.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.staService.client.RegisterMemberClient;
import com.zengchuihao.staService.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/staService/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    //根据日期获得当天的注册人数
    @GetMapping("getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day) {
        staService.getRegisterCount(day);
        return R.ok();
    }

    //获取前端的图表统计数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin, @PathVariable String end) {
        Map<String, Object> map =staService.getShowData(type, begin, end);
        return R.ok().data(map);
    }




}

