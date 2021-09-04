package com.zengchuihao.demo.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.demo.eduService.client.VodClient;
import com.zengchuihao.demo.eduService.entity.EduVideo;
import com.zengchuihao.demo.eduService.mapper.EduVideoMapper;
import com.zengchuihao.demo.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteCourseVideo(String courseId) {

        QueryWrapper<EduVideo> queryVideo = new QueryWrapper<>();
        queryVideo.eq("course_id", courseId);
        queryVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(queryVideo);

        ArrayList<String> videoIds = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();

            if(!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }

        if(videoIds.size()>0) {
            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
