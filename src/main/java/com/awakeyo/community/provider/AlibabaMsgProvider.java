package com.awakeyo.community.provider;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.awakeyo.community.util.NumberUtil;
import org.springframework.stereotype.Service;

/**
 * @author awakeyoyoyo
 * @className AlibabaMsgProvider
 * @description TODO
 * @date 2020-02-23 22:51
 */
@Service
public class AlibabaMsgProvider {
    public void sendMsg(){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13316311153");
        request.putQueryParameter("SignName", "与我常在blog");
        request.putQueryParameter("TemplateCode", "SMS_184121028");
        String code=NumberUtil.randomNumber();
        request.putQueryParameter("TemplateParam", "{code:"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            //todo redis缓存验证码 并且设置时间
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
