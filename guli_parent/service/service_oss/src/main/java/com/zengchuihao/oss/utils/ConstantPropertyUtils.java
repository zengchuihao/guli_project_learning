package com.zengchuihao.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertyUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keyscrect;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;


    public static String END_PONT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SCRECT;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_PONT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SCRECT = keyscrect;
        BUCKET_NAME = bucketname;
    }
}
