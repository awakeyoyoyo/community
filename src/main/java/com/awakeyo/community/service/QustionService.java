package com.awakeyo.community.service;

import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.Question;
import com.awakeyo.community.pojo.dto.QuestionDTO;
import com.awakeyo.community.pojo.dto.User;
import com.awakeyo.community.exception.CustomizeException;
import com.awakeyo.community.exception.QuestionErrorCode;
import com.awakeyo.community.mapper.QuestionMapper;
import com.awakeyo.community.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(questionDTOS);
        return pageResult;
    }

    public PageResult<QuestionDTO> getListByUserid(Integer id, Integer pageNo, Integer pageSize) {

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
        List<Question> questions=questionMapper.selectListByUser(id,pageBegin,pageSize);
        List<QuestionDTO> questionDTOS=new ArrayList<>();
        for (Question question:questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        PageResult<QuestionDTO> pageResult=new PageResult<>();
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(questionDTOS);
        return pageResult;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
        }
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void insertOrUpdate(Question question) {
        if (question.getId()==null){
            question.setGmtCreate(new Date().getTime());
            question.setGmtModified(new Date().getTime());
            questionMapper.insert(question);
        }
        else {
            Question updateQuestion=new Question();
            updateQuestion.setId(question.getId());
            updateQuestion.setDecription(question.getDecription());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setGmtModified(new Date().getTime());
            int row=questionMapper.updateByPrimaryKeySelective(updateQuestion);
            if (row!=1){
                throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
            }
        }
    }

    public QuestionDTO getByIdAndIncView(Integer id) {
        int row= questionMapper.updateViewCount(id);
        if (row<=0){
            throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
        }
        QuestionDTO questionDTO=new QuestionDTO();
        Question question=questionMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
