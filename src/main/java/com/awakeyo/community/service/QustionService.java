package com.awakeyo.community.service;

import com.awakeyo.community.dto.PageResult;
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
    public PageResult<QuestionDTO> getList(Integer pageNo, Integer pageSize) {

        Integer itemCount=questionMapper.selectAll();
        int  pageCount;
        if (itemCount/pageSize==0){
            pageCount=1;
        }else if (itemCount%pageSize==0){
            pageCount=itemCount/pageSize;
        }else {
            pageCount=itemCount/pageSize+1;
        }
        if (pageNo<1){
            pageNo=1;
        }
        if (pageNo>pageCount){
            pageNo=pageCount;
        }
        Integer pageBegin=pageSize*(pageNo-1);
        List<Question> questions=questionMapper.selectList(pageBegin,pageSize);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question:questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        PageResult<QuestionDTO> pageResult=new PageResult<>();
        pageResult.init(pageCount,pageNo,pageSize);
        pageResult.setReslts(questionDTOS);
        return pageResult;
    }
}
