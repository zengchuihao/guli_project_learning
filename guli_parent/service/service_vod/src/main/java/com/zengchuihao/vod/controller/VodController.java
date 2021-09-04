package com.zengchuihao.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import com.zengchuihao.vod.service.VodService;
import com.zengchuihao.vod.utils.ConstantVod;
import com.zengchuihao.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频接口
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {

        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }



    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {

        try {
            //1、初始化DefaultAcsClient对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVod.ACCESS_KEY_ID, ConstantVod.ACCESS_KEY_SECRET);
            //2、创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyCreateException(20001, "视频删除失败");
        }
    }


    //删除视频
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("idList") List<String> idList) {

        try {
            //1、初始化DefaultAcsClient对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVod.ACCESS_KEY_ID, ConstantVod.ACCESS_KEY_SECRET);
            //2、创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            String ids = StringUtils.join(idList.toArray(), ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyCreateException(20001, "视频删除失败");
        }

    }

    //获得视频播放凭证
    @GetMapping("getVideoAuth/{videoId}")
    public R getVideoAuth(@PathVariable String videoId) {

        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVod.ACCESS_KEY_ID, ConstantVod.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);

        } catch (ClientException e) {
            throw new MyCreateException(20001,"获取视频播放凭证失败");
        }

    }


}
