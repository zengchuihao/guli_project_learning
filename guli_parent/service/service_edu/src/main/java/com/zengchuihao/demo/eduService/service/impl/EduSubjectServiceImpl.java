package com.zengchuihao.demo.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.demo.eduService.entity.EduSubject;
import com.zengchuihao.demo.eduService.entity.excel.SubjectData;
import com.zengchuihao.demo.eduService.entity.subject.FirstSubject;
import com.zengchuihao.demo.eduService.entity.subject.SecondSubject;
import com.zengchuihao.demo.eduService.listen.EasyEexclListen;
import com.zengchuihao.demo.eduService.mapper.EduSubjectMapper;
import com.zengchuihao.demo.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-04
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new EasyEexclListen(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstSubject> getAllSubjectList() {

        List<FirstSubject> finalSubjectList = new ArrayList<>();

        //查询一级标题集合
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("parent_id","0");
        List<EduSubject> eduFirstSubject = baseMapper.selectList(queryWrapper1);
        //查询二级标题集合
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.ne("parent_id", "0");
        List<EduSubject> eduSecondSubject = baseMapper.selectList(queryWrapper2);

        for (int i = 0; i < eduFirstSubject.size(); i++) {

            EduSubject eduFirstSubjectList = eduFirstSubject.get(i);
            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(eduFirstSubjectList, firstSubject);
            finalSubjectList.add(firstSubject);

            List<SecondSubject> child = new ArrayList<>();

            for (int j = 0; j < eduSecondSubject.size(); j++) {

                EduSubject eduSecondSubjectList = eduSecondSubject.get(j);


                if(firstSubject.getId().equals(eduSecondSubjectList.getParentId())) {

                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(eduSecondSubjectList, secondSubject);
                    child.add(secondSubject);
                }
            }

            firstSubject.setChildren(child);

        }

        return finalSubjectList;
    }
}
