package com.zengchuihao.staService.schedule;

import com.zengchuihao.staService.service.StatisticsDailyService;
import com.zengchuihao.staService.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService service;

    @Scheduled(cron ="0 0 1 * * ?")
    public void task1() {
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        service.getRegisterCount(day);
    }

}
