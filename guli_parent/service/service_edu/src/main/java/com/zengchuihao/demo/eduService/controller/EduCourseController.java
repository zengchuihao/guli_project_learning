package com.zengchuihao.demo.eduService.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.zengchuihao.demo.eduService.entity.vo.CourseInfoVo;
import com.zengchuihao.demo.eduService.entity.vo.CoursePublishVo;
import com.zengchuihao.demo.eduService.entity.vo.CourseQuery;
import com.zengchuihao.demo.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {

        String courseId = eduCourseService.addCourse(courseInfoVo);
        return R.ok().data("courseId",courseId);

    }


    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("info",courseInfoVo);
    }


    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {

       eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }


    @GetMapping("getCoursePublishInfoVo/{courseId}")
    public R getCoursePublishInfoVo (@PathVariable String courseId) {

        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishInfoVo (courseId);
        return R.ok().data("coursePublishVo", coursePublishVo);
    }


    @PostMapping("publishStatus/{courseId}")
    public R publishStatus (@PathVariable String courseId) {

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    @PostMapping("getCourseList")
    public R getCourseList() {

        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    //课程条件查询带分页
    @PostMapping("coursePageCondition/{page}/{limit}")
    public R coursePageCondition(@PathVariable long page, @PathVariable long limit, @RequestBody CourseQuery courseQuery) {

        Map<String, Object> courseMap = eduCourseService.coursePageCondition(page, limit, courseQuery);
        return R.ok().data(courseMap);
    }


    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.deleteCourse(courseId);
        return R.ok();
    }









}

