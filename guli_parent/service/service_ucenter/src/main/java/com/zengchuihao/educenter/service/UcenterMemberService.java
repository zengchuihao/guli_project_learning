package com.zengchuihao.educenter.service;

import com.zengchuihao.educenter.entity.RegisterVo;
import com.zengchuihao.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-08-17
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenidMember(String openid);

    Integer countDailyRegister(String day);
}
