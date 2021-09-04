package com.zengchuihao.staService.client;

import com.zengchuihao.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface RegisterMemberClient {

    @GetMapping("/educenter/ucenter-member/countDailyRegister/{day}")
    public R countDailyRegister(@PathVariable("day") String day);

}
