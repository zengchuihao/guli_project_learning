package com.zengchuihao.vod.service;

import org.springframework.web.multipart.MultipartFile;


public interface VodService {
    String uploadVideo(MultipartFile file);
}
