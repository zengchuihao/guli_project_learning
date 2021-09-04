package com.zengchuihao.staService.service;

import com.zengchuihao.staService.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-24
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
