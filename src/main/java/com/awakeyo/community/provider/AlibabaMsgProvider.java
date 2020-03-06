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
import com.awakeyo.community.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author awakeyoyoyo
 * @className AlibabaMsgProvider
 * @description TODO
 * @date 2020-02-23 22:51
 */
@Service
@Slf4j
public class AlibabaMsgProvider {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${alibabaoss.accessKeyId}")
    private String accessKeyId;
    @Value("${alibabaoss.accessKeySecret}")
    private String accessKeySecret;
    public void sendMsg(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "与我常在blog");
        request.putQueryParameter("TemplateCode", "SMS_184121028");
        String code=NumberUtil.randomNumber();
        redisUtil.set(phone,code);
        redisUtil.expire(phone,300);
        request.putQueryParameter("TemplateParam", "{code:"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
            //存入缓存中5分钟
        } catch (ServerException e) {
            log.error("发送验证码失败错误信息{}",e.getMessage());
            e.printStackTrace();
        } catch (ClientException e) {
            log.error("发送验证码失败，错误信息{}",e.getMessage());
            e.printStackTrace();
        }
    }
}
