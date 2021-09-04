package com.zengchuihao.eduorder.client;

import com.zengchuihao.commonutils.order.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @PostMapping("/educenter/ucenter-member/getMemberOrderInfo/{id}")
    public UcenterMemberOrder getMemberOrderInfo(@PathVariable("id") String id);

}
