package com.awakeyo.community.config;

import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.util.IndexManagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author awakeyoyoyo
 * @className MultithreadScheduleTask
 * @description TODO
 * @date 2020-04-15 17:01
 */
@Component
public class MultithreadScheduleTask {
    @Autowired
    IndexManagerUtil indexManagerUtil;
    @Autowired
    ArticleMapper articleMapper;
    private Logger logger = LoggerFactory.getLogger(MultithreadScheduleTask.class);
    // cron接受cron表达式，根据cron表达式确定定时规则
    @Scheduled(cron="0 0 23 * * ?")  //每5秒执行一次
    public void Cron() throws IOException {
        //更新索引
        indexManagerUtil.indexCreate(articleMapper.selectAllArticles());
    }
}
