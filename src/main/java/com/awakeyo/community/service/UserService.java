package com.awakeyo.community.service;

import com.awakeyo.community.pojo.dto.User;
import com.awakeyo.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
