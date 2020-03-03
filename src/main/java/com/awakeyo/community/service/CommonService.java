package com.awakeyo.community.service;

import com.awakeyo.community.cache.CategoriesCache;
import com.awakeyo.community.exception.RedisException;
import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.mapper.CommentMapper;
import com.awakeyo.community.mapper.QuestionMapper;
import com.awakeyo.community.pojo.dto.WebMessageDto;
import com.awakeyo.community.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author awakeyoyoyo
 * @className CommonController
 * @description TODO
 * @date 2020-03-03 14:37
 */
@Service
public class CommonService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisUtil redisUtil;
    public WebMessageDto getMessage(){
        WebMessageDto webMessageDto=new WebMessageDto();
        webMessageDto.setTagCount(CategoriesCache.getInstance().get().size());
        Integer blogCount,questionCount,recordCount;
        try {
            blogCount=(Integer) redisUtil.get("blogCount");
            questionCount=(Integer) redisUtil.get("questionCount");
            recordCount=(Integer) redisUtil.get("recordCount");
        }catch (Exception e){
            throw new RedisException("Redis服务器炸了兄弟。。请帮忙联系一下站主");
        }
        if (blogCount==null){
            blogCount=articleMapper.selectAll();
            redisUtil.set("blogCount",blogCount);
        }
        if (questionCount==null){
            questionCount=questionMapper.selectAll();
            redisUtil.set("questionCount",questionCount);
        }
        if (recordCount==null){
            recordCount=commentMapper.selectAllByTopicId(10086);
            redisUtil.set("recordCount",recordCount);
        }
        webMessageDto.setBlogCount(blogCount);
        webMessageDto.setQuestionCount(questionCount);
        webMessageDto.setRecordCount(recordCount);
        return webMessageDto;
    }
}
