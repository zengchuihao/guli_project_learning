package com.zengchuihao.demo.eduService.service;

import com.zengchuihao.demo.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zengchuihao.demo.eduService.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-04
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);


    List<FirstSubject> getAllSubjectList();
}
