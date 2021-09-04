package com.zengchuihao.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.educms.entity.CrmBanner;
import com.zengchuihao.educms.mapper.CrmBannerMapper;
import com.zengchuihao.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-14
 */
@Service
@Cacheable(key = "'selectIndexList'", value = "banner")
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> getAllBanner() {

        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
