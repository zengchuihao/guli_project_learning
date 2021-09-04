package com.zengchuihao.educenter.controller;

import com.google.gson.Gson;
import com.zengchuihao.commonutils.JwtUtils;
import com.zengchuihao.educenter.entity.UcenterMember;
import com.zengchuihao.educenter.service.UcenterMemberService;
import com.zengchuihao.educenter.utils.ConstantWX;
import com.zengchuihao.educenter.utils.HttpClientUtils;
import com.zengchuihao.servicebase.exceptionhandler.MyCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("login")
    public String getWxCode() {

        // 微信开放平台授权baseUrl
        // 根据appid和公司域名访问腾讯固定地址，返回含有临时令牌的code和 state的地址，直接调用本地callback方法
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";


        String redirectUrl = ConstantWX.REDIRECT_URL;

        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(baseUrl, ConstantWX.WX_OPEN_APP_ID, redirectUrl, "zengchuihao");

        return "redirect:" + url;
    }

    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        try {

            //通过临时令牌code，请求微信提供的固定地址，获得访问凭证access_token和每个微信的唯一表示openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl, ConstantWX.WX_OPEN_APP_ID, ConstantWX.WX_OPEN_APP_SECRET, code);

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
           // System.out.println("accessTokenInfo------" +accessTokenInfo );

            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //将获取到的用户信息存储到数据库中
            UcenterMember member = ucenterMemberService.getOpenidMember(openid);
            if (member == null) {

                //通过访问凭证和唯一标识访问固定地址，得到用户的基本信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("---++++++--======"+userInfo);

                HashMap mapUserInfo = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) mapUserInfo.get("nickname");
                String headimgurl = (String) mapUserInfo.get("headimgurl");

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);
                ucenterMemberService.save(member);
            }

            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
