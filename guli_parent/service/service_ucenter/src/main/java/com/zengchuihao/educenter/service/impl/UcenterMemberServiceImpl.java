package com.zengchuihao.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zengchuihao.commonutils.JwtUtils;
import com.zengchuihao.commonutils.MD5;
import com.zengchuihao.commonutils.R;
import com.zengchuihao.educenter.entity.RegisterVo;
import com.zengchuihao.educenter.entity.UcenterMember;
import com.zengchuihao.educenter.mapper.UcenterMemberMapper;
import com.zengchuihao.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-08-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String>  redisTemplate;

    @Override
    public String login(UcenterMember member) {

        String password = member.getPassword();
        String mobile = member.getMobile();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyCreateException(20001, "手机号或者密码不能为空");
        }

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(queryWrapper);

        if(mobileMember == null) {
            throw new MyCreateException(20001, "没有该手机号用户");
        }

        if(!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new MyCreateException(20001, "密码验证失败");
        }

        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {

        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //redisTemplate.opsForValue().set("code", code, 5, TimeUnit.MINUTES);

        if(StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new MyCreateException(20001, "用户名、手机号、密码和验证码不能为空");
        }

        //验证码是否相同
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobileCode)) {
            throw new MyCreateException(20001, "验证码不正确");
        }

        //手机号是否被注册过
        Integer row = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (row > 0) {
            throw new MyCreateException(20001, "手机号已被注册");
        }

        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setMobile(mobile);
        member.setIsDisabled(false);
        member.setAvatar("https://guli-institute-99511.oss-cn-guangzhou.aliyuncs.com/1111111111.jpg");
        this.save(member);

    }

    @Override
    public UcenterMember getOpenidMember(String openid) {

        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer countDailyRegister(String day) {

        return baseMapper.countRegister(day);
    }

}
