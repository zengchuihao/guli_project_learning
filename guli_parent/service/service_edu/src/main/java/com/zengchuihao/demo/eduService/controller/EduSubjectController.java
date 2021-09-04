package com.zengchuihao.demo.eduService.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.subject.FirstSubject;
import com.zengchuihao.demo.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/eduservice/edusubject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;


    @PostMapping("addsubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    //查询所有的一二级分类
    @GetMapping("getsubjectlist")
    public R getSubjectList () {

        List<FirstSubject> list = subjectService.getAllSubjectList();

        return R.ok().data("list", list);
    }

}

