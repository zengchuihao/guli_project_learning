package com.zengchuihao.demo.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.demo.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-07-26
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> teacherPageList(Page<EduTeacher> pageParam);
}
