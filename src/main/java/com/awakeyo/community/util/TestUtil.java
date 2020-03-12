package com.awakeyo.community.util;

import com.awakeyo.community.mapper.PermissionMapper;
import com.awakeyo.community.mapper.RoleMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.User;
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
        String[] as={"lqh666***","123456","83320420","Lokie201609","123456789","qq2316741037",
                "kanade","baogenb","13703032373"};
        for (String x:as)
        System.out.println(ShiroMd5Util.shiroEncryption(x,"OGTdToSJ6t/dsnVPaH2g9A=="));
    }
}