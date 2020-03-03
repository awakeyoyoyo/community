package com.awakeyo.community.util;

import com.awakeyo.community.pojo.User;
import com.github.afkbrb.avatar.Avatar;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author awakeyoyoyo
 * @className RedisTest
 * @description TODO
 * @date 2020-02-24 22:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;
    @Test
   public void test(){
        User user=new User();
        user.setName("lqhao");
        user.setId(1000);
        String x=(String) redisUtil.get("blogCount");
        if (x==null){
            System.out.println("gg");
        }

   }
    @Test
    public void test2(){
        Avatar avatar = new Avatar();
        avatar.saveAsPNG("avatar.png");
    }
}
