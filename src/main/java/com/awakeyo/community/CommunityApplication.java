package com.awakeyo.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "com.awakeyo.community.mapper")
//@ImportResource("classpath:config/spring-aop.xml")
@SpringBootApplication
@EnableScheduling //开启定时任务
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }

}
