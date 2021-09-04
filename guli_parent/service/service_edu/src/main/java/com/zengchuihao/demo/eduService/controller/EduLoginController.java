package com.zengchuihao.demo.eduService.controller;

import com.zengchuihao.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {


    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://guli-institute-99511.oss-cn-guangzhou.aliyuncs.com/2021/08/04/27e33f0bfbb74e5aa69311a6ad387148file.png");
    }


}
