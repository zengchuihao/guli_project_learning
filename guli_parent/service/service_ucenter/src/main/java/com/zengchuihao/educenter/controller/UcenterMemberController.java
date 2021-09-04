package com.zengchuihao.educenter.controller;


import com.zengchuihao.commonutils.JwtUtils;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.commonutils.order.UcenterMemberOrder;
import com.zengchuihao.educenter.entity.RegisterVo;
import com.zengchuihao.educenter.entity.UcenterMember;
import com.zengchuihao.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-17
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;


    //登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member) {

        String token = ucenterMemberService.login(member);
        return R.ok().data("token", token);
    }

    //根据自定义验证码注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {

        ucenterMemberService.register(registerVo);
        return R.ok();

    }

    //根据token获取用户信息
    @GetMapping("getMember")
    public R getMember (HttpServletRequest request) {

        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(id);
        //System.out.println("member的值是=="+member);
        return R.ok().data("member", member);
    }

    //根据用户id获得用户信息，用于生成订单
    @PostMapping("getMemberOrderInfo/{id}")
    public UcenterMemberOrder getMemberOrderInfo(@PathVariable String id) {

        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //根据日期查询注册人数
    @GetMapping("countDailyRegister/{day}")
    public R countDailyRegister(@PathVariable String day) {

        Integer count = ucenterMemberService.countDailyRegister(day);
        return R.ok().data("count", count);
    }


}

