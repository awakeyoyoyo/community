package com.awakeyo.community.config;

import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.util.IndexManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className AfterStartDo
 * @description TODO
 * @date 2020-04-15 16:35
 */
@Component
public class AfterStartDo implements CommandLineRunner {
    @Autowired
    IndexManagerUtil indexManagerUtil;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(">>>>>>>>>>>>>>>服务启动执行，操作数据库新建索引等操作<<<<<<<<<<<<<");
        //开启新线程来新建索引
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Article> articles;
                articles = articleMapper.selectAllArticles();
// 创建索引一般需要数秒种，为避免阻塞主线程影响业务，开启新线程执行
                try {
                    indexManagerUtil.indexCreate(articles);
//                    System.out.println(">>>>>>>>>>>>>>>操作数据库新建索引完成<<<<<<<<<<<<<");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
