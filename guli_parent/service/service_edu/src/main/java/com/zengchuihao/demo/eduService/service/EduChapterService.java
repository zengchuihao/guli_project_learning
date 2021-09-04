package com.zengchuihao.demo.eduService.service;

import com.zengchuihao.demo.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zengchuihao.demo.eduService.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
public interface EduChapterService extends IService<EduChapter> {

   List<ChapterVo> getChapterList(String courseId);

    Boolean deleteChapter(String chapterId);

    void deleteCourseChapter(String courseId);
}
