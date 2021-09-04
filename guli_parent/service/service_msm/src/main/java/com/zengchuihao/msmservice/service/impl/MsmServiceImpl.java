package com.zengchuihao.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zengchuihao.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        //声明reques的基本信息（直接套用模板）
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置request的基本参数，根据项目情况进行更改
        request.putQueryParameter("PhoneNumbers", phone); //电话号码
        request.putQueryParameter("SignName", "我的谷粒在线教育网站"); //短信服务标签名
        request.putQueryParameter("TemplateCode", "sjfajs");  //阿里云短信模板的code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //生成的验证码

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }
}
