package com.awakeyo.community.service;

import com.awakeyo.community.exception.CustomizeException;
import com.awakeyo.community.exception.QuestionErrorCode;
import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.mapper.UserMapper;
import com.awakeyo.community.pojo.Article;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.User;
import com.awakeyo.community.pojo.dto.ArticleDto;
import com.awakeyo.community.pojo.dto.CommentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author awakeyoyoyo
 * @className ArticleService
 * @description TODO
 * @date 2020-02-27 20:33
 */
@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentReplyService commentReplyService;
    public void insertOrUpdate(Article article) {
        if (article.getId()==null){
            article.setGmtCreate(new Date().getTime());
            article.setGtmModifiled(new Date().getTime());
            article.setLikeCount(0);
            article.setViewCount(0);
            article.setCommentCount(0);
            articleMapper.insert(article);
        }
        else {
            Article updateQuestion=new Article();
            updateQuestion.setId(article.getId());
            updateQuestion.setDecription(article.getDecription());
            updateQuestion.setTitle(article.getTitle());
            updateQuestion.setContent(article.getContent());
            updateQuestion.setTag(article.getTag());
            updateQuestion.setGtmModifiled(new Date().getTime());
            int row=articleMapper.updateByPrimaryKeySelective(updateQuestion);
            if (row!=1){
                throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
            }
        }
    }

    public ArticleDto geiArtleById(Integer id) {
        int row= articleMapper.updateViewCount(id);
        if (row<=0){
            throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
        }
        ArticleDto articleDto=new ArticleDto();
        Article article=articleMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(article,articleDto);
        User user=userMapper.selectByPrimaryKey(article.getCreator());
        articleDto.setUser(user);
        //评论回复=。=
        List<CommentDTO> comments=commentReplyService.getCommentsReplyTopicId("article",article.getId());
        if (comments==null){
            throw new CustomizeException("别乱调戏接口-。-");
        }
        articleDto.setCommentDTOs(comments);
        return articleDto;
    }

    public PageResult<ArticleDto> getListSearch(String search, Integer pageNo, Integer pageSize) {
        search= StringUtils.replace(search," ","|");
        Integer itemCount=articleMapper.selectAllSearch(search);
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
        List<Article> articles=articleMapper.selectListSearch(search,pageBegin,pageSize);
        List<ArticleDto> articleDtos=new ArrayList<>();
        for (Article article:articles) {
            User user=userMapper.selectByPrimaryKey(article.getCreator());
            ArticleDto articleDto=new ArticleDto();
            BeanUtils.copyProperties(article,articleDto);
            articleDto.setUser(user);
            articleDtos.add(articleDto);
        }
        PageResult<ArticleDto> pageResult=new PageResult<>();
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(articleDtos);
        return pageResult;
    }

    public PageResult<ArticleDto> getList(Integer pageNo, Integer pageSize) {
        Integer itemCount=articleMapper.selectAll();
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
        List<Article> articles=articleMapper.selectList(pageBegin,pageSize);
        List<ArticleDto> articleDtos=new ArrayList<>();
        for (Article article:articles) {
            User user=userMapper.selectByPrimaryKey(article.getCreator());
            ArticleDto articleDto=new ArticleDto();
            BeanUtils.copyProperties(article,articleDto);
            articleDto.setUser(user);
            articleDtos.add(articleDto);
        }
        PageResult<ArticleDto> pageResult=new PageResult<>();
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(articleDtos);

        return pageResult;
    }

    public ArticleDto getByIdAndIncView(Integer topId) {
        int row= articleMapper.updateViewCount(topId);
        if (row<=0){
            throw new CustomizeException(QuestionErrorCode.QUESTION_NOT_FOUND.getMessage());
        }
        ArticleDto articleDto=new ArticleDto();
        Article article=articleMapper.selectByPrimaryKey(topId);
        BeanUtils.copyProperties(article,articleDto);
        User user=userMapper.selectByPrimaryKey(article.getCreator());
        articleDto.setUser(user);
        //评论回复=。=
        List<CommentDTO> comments=commentReplyService.getCommentsReplyTopicId("article",article.getId());
        if (comments==null){
            throw new CustomizeException("别乱调戏接口-。-");
        }
        articleDto.setCommentDTOs(comments);
        return articleDto;
    }
}
