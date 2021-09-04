package com.zengchuihao.demo.eduService.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.entity.EduChapter;
import com.zengchuihao.demo.eduService.entity.vo.ChapterVo;
import com.zengchuihao.demo.eduService.mapper.EduChapterMapper;
import com.zengchuihao.demo.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @PostMapping("getChapterList/{courseId}")
    public R getChapterList(@PathVariable String courseId) {

        List<ChapterVo> list = eduChapterService.getChapterList(courseId);
        return R.ok().data("list", list);

    }


    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {

        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {

        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter (@PathVariable String chapterId) {

        Boolean result = eduChapterService.deleteChapter(chapterId);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}

