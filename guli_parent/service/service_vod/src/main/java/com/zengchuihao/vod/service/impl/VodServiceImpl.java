package com.zengchuihao.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.zengchuihao.vod.service.VodService;
import com.zengchuihao.vod.utils.ConstantVod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {

        try {

            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream is = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(ConstantVod.ACCESS_KEY_ID, ConstantVod.ACCESS_KEY_SECRET, title, fileName, is);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
