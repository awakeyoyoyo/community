package com.awakeyo.community.util;

import com.awakeyo.community.mapper.PermissionMapper;
import com.awakeyo.community.mapper.RoleMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className TestUtil
 * @description TODO
 * @date 2020-03-11 21:40
 */
@SpringBootTest
public class TestUtil {
        @Test
    public void getRolesByUserId(){

    }

}