package com.zengchuihao.demo.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.zengchuihao.demo.eduService.entity.vo.TeacherQuery;
import com.zengchuihao.demo.eduService.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-07-26
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;


    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> teacherList = eduTeacherService.list(null);
        return R.ok().data("items", teacherList);
    }

    @DeleteMapping({"{id}"})
    public R removeById(@PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }else return R.error();
    }

    @ApiOperation(value = "讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页数量", required = true) @PathVariable long limit) {

        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        eduTeacherService.page(eduTeacherPage,null);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows", records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "讲师条件查询分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody TeacherQuery teacherQuery) {

        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.gt("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.lt("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");

        eduTeacherService.page(eduTeacherPage, wrapper);

        List<EduTeacher> records = eduTeacherPage.getRecords();
        long total = eduTeacherPage.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows", records);
        return R.ok().data(map);

    }

    @ApiOperation(value = "讲师添加方法 ")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else return R.error();

    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("findTeacherById/{id}")
    public R findTeacherById(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else return R.error();
    }



}

