package com.zengchuihao.msmservice.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.msmservice.service.MsmService;
import com.zengchuihao.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msmservice")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate; //注入springboot封装的redis模板


    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {

        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok().data("code", code);
        }
        code = RandomUtil.getSixBitRandom();
        /*Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = msmService.send(param, phone);*/

        /*if(isSend) {*/
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok().data("code", code);
     /*   } else {
            return R.error().message("验证码发送失败");
        }*/


    }

}
