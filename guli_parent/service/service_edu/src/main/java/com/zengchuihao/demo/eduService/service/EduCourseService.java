package com.zengchuihao.demo.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseFrontVo;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseWebVo;
import com.zengchuihao.demo.eduService.entity.vo.CourseInfoVo;
import com.zengchuihao.demo.eduService.entity.vo.CoursePublishVo;
import com.zengchuihao.demo.eduService.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
public interface EduCourseService extends IService<EduCourse> {


    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfoVo(String courseId);

    Map<String, Object> coursePageCondition(long page, long limit, CourseQuery courseQuery);

    void deleteCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParams, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseFrontById(String courseId);
}
