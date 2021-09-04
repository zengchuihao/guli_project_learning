package com.zengchuihao.eduorder.client;

import com.zengchuihao.commonutils.order.CourseWebOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id得到课程的前端信息，用于生成订单
    @PostMapping("/eduservice/coursefront/getCourseOrderInfo/{courseId}")
    public CourseWebOrder getCourseOrderInfo(@PathVariable("courseId") String courseId);

}
