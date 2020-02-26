package com.awakeyo.community.service;

import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.exception.RedisException;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.pojo.dto.RegisterDto;
import com.awakeyo.community.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author awakeyoyoyo
 * @className UserService
 * @description TODO
 * @date 2019-12-10 20:52
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    public void createOrUpdate(User user) {
        if (userMapper.selectByaccoun_id(user.getAccountId())==null){
            user.setGmtCreate(new Date().getTime());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            user.setGmtModified(user.getGmtCreate());
            userMapper.updateTokenByAccoundId(user.getToken(),user.getAccountId());
        }
    }

    public WebResponse<User> login(String phone, String password, boolean remember, HttpServletResponse response) {
        User user=userMapper.selectByPhoneAndPassowrd(phone,password);
        if (user==null){
            return WebResponse.createByErrorMessage("手机错误或者密码错误");
        }
        else {
            if (remember){
                String token= UUID.randomUUID().toString();
                user.setToken(token);
                createOrUpdate(user);
                Cookie cookie=new Cookie("token",token);
                cookie.setMaxAge(60*60*24);
                response.addCookie(cookie);
            }
            return WebResponse.createBySuccess(user);
        }
    }

    public WebResponse register(RegisterDto registerDto) {
        //判断验证码是否正确
        String targetCode=null;
        try {
            targetCode=(String) redisUtil.get(registerDto.getPhone());
        }catch (Exception e){
            throw new RedisException("Redis服务器炸了兄弟。。请帮忙联系一下站主");
        }
        if (targetCode==null){
            return WebResponse.createByErrorMessage("验证码已经过期，请重新发送");
        }
        if (targetCode==registerDto.getCode()){
            User user=new User();
            user.setName(registerDto.getUsername());
            user.setGmtCreate(new Date().getTime());
            user.setBio("此人非常懒，还没有个人签名");
            user.setPassword(registerDto.getPassword());
            Random random=new Random();
            int i=random.nextInt(4);
            String avatarUrl="https://oss.awakeyoyoyo.com/community/avactor"+i+".png";
            user.setAvatarUrl(avatarUrl);
            user.setAccountId(registerDto.getPhone());
            user.setGmtModified(new Date().getTime());
            userMapper.insert(user);
            return WebResponse.createBySuccess();
        }else {
            return WebResponse.createByErrorMessage("验证码错误，请重新输入");
        }

    }
}
