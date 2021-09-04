package com.zengchuihao.demo.eduService.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.demo.eduService.client.VodClient;
import com.zengchuihao.demo.eduService.entity.EduVideo;
import com.zengchuihao.demo.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-06
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {

        eduVideoService.save(eduVideo);
        return R.ok();
    }

    @GetMapping("getVideo/{id}")
    public R getVideo (@PathVariable String id) {

        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video", video);
    }

    //使用微服务删除小节及小节内的视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo (@PathVariable String id) {

        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)) {

            vodClient.removeAlyVideo(videoSourceId);
        }

        eduVideoService.removeById(id);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {

        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

