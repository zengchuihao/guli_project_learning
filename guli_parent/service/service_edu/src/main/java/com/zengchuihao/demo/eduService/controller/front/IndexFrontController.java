package com.zengchuihao.demo.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.zengchuihao.demo.eduService.service.EduCourseService;
import com.zengchuihao.demo.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;


    @GetMapping("indexCourseAndTeacher")
    public R index() {

        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id").last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseWrapper);

        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id").last("limit 2");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherWrapper);

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
