package com.awakeyo.community.service;

import com.awakeyo.community.common.WebResponse;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    public void createOrUpdate(User user) {
        if (userMapper.selectByaccoun_id(user.getAccountId())<0){
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
}
