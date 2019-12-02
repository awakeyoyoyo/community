package com.awakeyo.community.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void selectByPrimaryKey() {
        System.out.println(userMapper.selectByPrimaryKey(1));
    }
}