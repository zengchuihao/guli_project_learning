package com.zengchuihao.demo.eduService.mapper;

import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseWebVo;
import com.zengchuihao.demo.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishInfo (String courseId);

    CourseWebVo getCourseFrontById(String courseId);
}
