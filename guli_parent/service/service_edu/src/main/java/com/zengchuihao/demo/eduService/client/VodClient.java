package com.zengchuihao.demo.eduService.client;


import com.zengchuihao.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component
@FeignClient(value = "service-vod", fallback = VodFileDegradeFeignClient.class)

public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    @DeleteMapping("/eduService/edu-video/deleteBatch")
    public R deleteBatch(@RequestParam("idList") List<String> idList);

}


