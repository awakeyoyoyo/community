package com.awakeyo.community.util;

import com.awakeyo.community.mapper.RoleMapper;
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
    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void getRolesByUserId(){
     List<String> roles=roleMapper.getRolesByUserId(8);
     System.out.println(roles.toString());
    }
}