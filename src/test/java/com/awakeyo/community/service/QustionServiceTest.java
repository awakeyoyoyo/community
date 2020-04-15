package com.awakeyo.community.service;

import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.util.IndexManagerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class QustionServiceTest {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    IndexManagerUtil indexManagerUtil;
    @Test
    void getListByUserId() throws Exception {
        List<Article> articles=new ArrayList<>();
        articles=articleMapper.selectAllArticles();
        indexManagerUtil.indexCreate(articles);
//        System.out.println(indexManagerUtil.indexSearch("内存").toString());
    }
}