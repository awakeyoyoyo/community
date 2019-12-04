package com.awakeyo.community.service;

import com.awakeyo.community.dto.Question;
import com.awakeyo.community.dto.QuestionDTO;
import com.awakeyo.community.dto.User;
import com.awakeyo.community.mapper.QuestionMapper;
import com.awakeyo.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className QustionService
 * @description TODO
 * @date 2019-12-04 23:08
 */
@Service
public class QustionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<QuestionDTO> getList() {
        List<Question> questions=questionMapper.selectList();
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question:questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
