package com.zengchuihao.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.zengchuihao.oss.service.OssService;
import com.zengchuihao.oss.utils.ConstantPropertyUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadOssFile(MultipartFile file) {

        String endpoint = ConstantPropertyUtils.END_PONT;
        String accessKeyId = ConstantPropertyUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertyUtils.ACCESS_KEY_SCRECT;
        String bucketName = ConstantPropertyUtils.BUCKET_NAME;


        try {

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream is = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid+fileName;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath+"/"+fileName;

            // 创建PutObjectRequest对象。
            // 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            //PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, is);
            ossClient.putObject(bucketName, fileName, is);
            ossClient.shutdown();
            //URL
            //https://guli-institute-99511.oss-cn-guangzhou.aliyuncs.com/00000000.jpg
            String url= "https://"+bucketName+"."+endpoint+"/"+fileName;

            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}



