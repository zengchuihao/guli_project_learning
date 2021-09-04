package com.zengchuihao.staService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.staService.client.RegisterMemberClient;
import com.zengchuihao.staService.entity.StatisticsDaily;
import com.zengchuihao.staService.mapper.StatisticsDailyMapper;
import com.zengchuihao.staService.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-24
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private RegisterMemberClient registerMemberClient;

    @Override
    public void getRegisterCount(String day) {

        //先删除表中相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("gmt_create", day);
        baseMapper.delete(wrapper);

        R r = registerMemberClient.countDailyRegister(day);
        Integer count = (Integer) r.getData().get("count");

        //将得到的数据插入数据库统计表中
        StatisticsDaily sta = new StatisticsDaily();
        sta.setDateCalculated(day);
        sta.setRegisterNum(count);
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);

    }

    //根据条件查询数据，并将数据封装成相应的前端json格式返回
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {

        //根据条件查出相应的list集合
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(queryWrapper);
        //System.out.println("+++++++++"+staList);

        //将list集合封装成相应的前端json格式返回
        ArrayList<String> date_calculatedList = new ArrayList<>();
        ArrayList<Integer> typeList = new ArrayList<>();

        for (StatisticsDaily statisticsDaily : staList) {
            date_calculatedList.add(statisticsDaily.getDateCalculated());

            switch (type) {
                case "login_num":
                    typeList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num":
                    typeList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num":
                    typeList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    typeList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;

            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("typeList", typeList);
        return map;
    }
}
