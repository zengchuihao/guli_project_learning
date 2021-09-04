package com.zengchuihao.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.educms.entity.CrmBanner;
import com.zengchuihao.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-08-14
 */
@RestController
@RequestMapping("/educms/admin_banner")
@CrossOrigin
public class AdminBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {

        Page<CrmBanner> crmBannerPage = new Page<>(page, limit);
        crmBannerService.page(crmBannerPage, null);
        return R.ok().data("list", crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());
    }

    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {

        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {

        crmBannerService.updateById(crmBanner);
        return R.ok();
    }

    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {

        crmBannerService.removeById(id);
        return R.ok();
    }

    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id) {

        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("crmBanner", crmBanner);
    }
}

