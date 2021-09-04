package com.zengchuihao.educms.controller;


import com.zengchuihao.commonutils.R;
import com.zengchuihao.educms.entity.CrmBanner;
import com.zengchuihao.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/educms/front_banner")
@CrossOrigin
public class FrontBannerController {

    @Autowired
    private CrmBannerService crmBannerService;


    @GetMapping("getBanner")
    public R getBanner() {

       List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list",list);
    }

}

