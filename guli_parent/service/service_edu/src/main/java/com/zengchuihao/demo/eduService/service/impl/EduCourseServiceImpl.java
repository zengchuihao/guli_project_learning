package com.zengchuihao.demo.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.demo.eduService.entity.EduCourse;
import com.zengchuihao.demo.eduService.entity.EduCourseDescription;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseFrontVo;
import com.zengchuihao.demo.eduService.entity.frontVo.CourseWebVo;
import com.zengchuihao.demo.eduService.entity.vo.CourseInfoVo;
import com.zengchuihao.demo.eduService.entity.vo.CoursePublishVo;
import com.zengchuihao.demo.eduService.entity.vo.CourseQuery;
import com.zengchuihao.demo.eduService.mapper.EduCourseMapper;
import com.zengchuihao.demo.eduService.service.EduChapterService;
import com.zengchuihao.demo.eduService.service.EduCourseDescriptionService;
import com.zengchuihao.demo.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengchuihao.demo.eduService.service.EduVideoService;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduCourseDescriptionService descriptionService;



    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert == 0) {
            throw new MyCreateException(20001,"添加课程失败");
        }

        String cid = eduCourse.getId();


        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {

        CourseInfoVo courseInfoVo = new CourseInfoVo();

        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int rows = baseMapper.updateById(eduCourse);

        if(rows == 0) {
            throw new MyCreateException(20001,"数据更新失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublishVo getCoursePublishInfoVo(String courseId) {

        return baseMapper.getPublishInfo(courseId);
    }

    @Override
    public Map<String, Object> coursePageCondition(long page, long limit, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        Page<EduCourse> coursePage = new Page<>(page, limit);

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        if( !StringUtils.isEmpty(title) ) {
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.gt("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.lt("gmt_create", end);
        }

        queryWrapper.orderByDesc("gmt_create");

        //将query对象装入page对象当中
        baseMapper.selectPage(coursePage, queryWrapper);
        //通过装有查询对象的分页对象调用getRecord方法得到带条件的分页记录
        List<EduCourse> records = coursePage.getRecords();
        long total = coursePage.getTotal();

        HashMap<String, Object> courseMap = new HashMap<>();
        courseMap.put("total", total);
        courseMap.put("records", records);
        return courseMap;
    }

    @Override
    public void deleteCourse(String courseId) {

        //1、删除课程中小节的内容
        eduVideoService.deleteCourseVideo(courseId);
        //2、删除课程中章节的内容
        eduChapterService.deleteCourseChapter(courseId);
        //3、删除课程中描述的内容
        eduCourseDescriptionService.removeById(courseId);
        //4、删除课程本身
        int i = baseMapper.deleteById(courseId);
        if(i == 0) {
            throw new MyCreateException(20001,"删除数据异常");
        }


    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParams, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();

        //是否有一级分类查询需求
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        //二级分类查询
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        //根据销售量排序
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        //根据创建时间排序
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        //根据价格排序
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        //将查询对象放入分页对象当中
        baseMapper.selectPage(pageParams, queryWrapper);

        List<EduCourse> records = pageParams.getRecords();
        long current = pageParams.getCurrent();
        long size = pageParams.getSize();
        long pages = pageParams.getPages();
        long total = pageParams.getTotal();
        boolean hasNext = pageParams.hasNext();
        boolean hasPrevious = pageParams.hasPrevious();

        HashMap<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        map.put("pages", pages);
        map.put("current", current);
        map.put("size", size);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getCourseFrontById(String courseId) {
        return baseMapper.getCourseFrontById(courseId);
    }
}
