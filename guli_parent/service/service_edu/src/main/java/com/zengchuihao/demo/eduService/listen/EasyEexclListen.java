package com.zengchuihao.demo.eduService.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.demo.eduService.entity.EduSubject;
import com.zengchuihao.demo.eduService.entity.excel.SubjectData;
import com.zengchuihao.demo.eduService.service.EduSubjectService;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;

public class EasyEexclListen extends AnalysisEventListener<SubjectData> {

    private EduSubjectService subjectService;

    public EasyEexclListen(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public EasyEexclListen() {
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        if(subjectData == null) {
            throw new MyCreateException(20001,"表格数据为空");
        }

        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getFirstSubject());
        if(existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getFirstSubject());
            subjectService.save(existOneSubject);
        }

        String pid= existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getSecondSubject(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getSecondSubject());
            subjectService.save(existTwoSubject);
        }


    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    public EduSubject existOneSubject(EduSubjectService subjectService, String name) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id","0");
        EduSubject oneEduSubject = subjectService.getOne(queryWrapper);
        return oneEduSubject;

    }


    public EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id",pid);
        EduSubject twoEduSubject = subjectService.getOne(queryWrapper);
        return twoEduSubject;

    }


}
