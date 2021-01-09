package com.awakeyo.community.service;

import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.util.IndexManagerUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class QustionServiceTest {
    @Autowired
    private TestAopService testAopService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test(){
        String user = testAopService.findUserById();
        System.out.println("<><><><><><><><><><><><><>");
        testAopService.addUser();
    }
}