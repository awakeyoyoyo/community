package com.awakeyo.community.mapper;

import com.awakeyo.community.dto.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Test
    void selectByPrimaryKey() {
        for (int i=6;i<=20;i++){
            Question question=new Question();
            question.setTitle(String.valueOf(i));
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setGmtModified(new Date().getTime());
            question.setGmtCreate(new Date().getTime());
            question.setCreator(8);
            question.setDecription("desc");
            questionMapper.insert(question);
        }
    }

}