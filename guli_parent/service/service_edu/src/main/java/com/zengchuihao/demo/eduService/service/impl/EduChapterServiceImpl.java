package com.zengchuihao.demo.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.demo.eduService.entity.EduChapter;
import com.zengchuihao.demo.eduService.entity.EduVideo;
import com.zengchuihao.demo.eduService.entity.vo.ChapterVo;
import com.zengchuihao.demo.eduService.entity.vo.VideoVo;
import com.zengchuihao.demo.eduService.mapper.EduChapterMapper;
import com.zengchuihao.demo.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengchuihao.demo.eduService.service.EduVideoService;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterList(String courseId) {

        List<ChapterVo> finalChapterList = new ArrayList<>();

        //1、获得数据库中章节的list集合
        QueryWrapper<EduChapter> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(queryWrapper1);

        //2、获得数据库中小节的list集合
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(queryWrapper2);



        //3、遍历章节的list集合，赋值给chaptervo对象
        for (int i = 0; i < eduChapters.size(); i++) {

            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            List<VideoVo> children = new ArrayList<>();

            //4、遍历小节的list集合，找出该章节对应的所有小节
            for (int j = 0; j < eduVideos.size(); j++) {
                EduVideo eduVideo = eduVideos.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    videoVo.setTitle(eduVideo.getTitle());
                    videoVo.setId(eduVideo.getId());
                    videoVo.setVideoSourceId(eduVideo.getVideoSourceId());
                    children.add(videoVo);
                }
                chapterVo.setChildren(children);
            }
            finalChapterList.add(chapterVo);
        }


        return finalChapterList;
    }


    //根据章节中是否有小节删除章节
    @Override
    public Boolean deleteChapter (String chapterId) {

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(queryWrapper);

        if (count > 0) {
            throw new MyCreateException(20001, "删除章节失败");
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    @Override
    public void deleteCourseChapter(String courseId) {

        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
