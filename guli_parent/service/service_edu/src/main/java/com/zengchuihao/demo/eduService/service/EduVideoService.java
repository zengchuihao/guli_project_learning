package com.zengchuihao.demo.eduService.service;

import com.zengchuihao.demo.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteCourseVideo(String courseId);
}
