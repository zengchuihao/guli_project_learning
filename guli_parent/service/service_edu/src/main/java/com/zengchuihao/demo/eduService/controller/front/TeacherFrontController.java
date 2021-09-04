package com.zengchuihao.demo.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.zengchuihao.demo.eduService.service.EduCourseService;
import com.zengchuihao.demo.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    //查询前端的讲师分页数据
    @PostMapping("frontteacherlist/{page}/{limit}")
    public R teacherPageList(@PathVariable long page, @PathVariable long limit) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.teacherPageList(pageParam);
        return R.ok().data( map);
    }


    //根据讲师id查询前端讲师的信息和他的所有课程信息
    @GetMapping("getFrontTeacherInfo/{teacherId}")
    public R getFrontTeacherInfo(@PathVariable String teacherId) {

        EduTeacher frontTeacher = eduTeacherService.getById(teacherId);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courses = eduCourseService.list(wrapper);

        return R.ok().data("frontTeacher", frontTeacher).data("courses", courses);
    }


}
