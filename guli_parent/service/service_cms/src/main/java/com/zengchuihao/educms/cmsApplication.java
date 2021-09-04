package com.zengchuihao.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zengchuihao")
@MapperScan("com.zengchuihao.educms.mapper")
public class cmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(cmsApplication.class, args);
    }

}
