package com.zengchuihao.demo.eduService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order", fallback = OrderFileDegradeFeignClient.class)
public interface OrderClient {

    //根据用户id和课程id查询课程是否购买state
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable(value = "courseId") String courseId, @PathVariable(value = "memberId") String memberId);

}
