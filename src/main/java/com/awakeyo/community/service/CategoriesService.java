package com.awakeyo.community.service;

import com.awakeyo.community.mapper.ArticleMapper;
import com.awakeyo.community.pojo.PageResult;
import com.awakeyo.community.pojo.dto.ArticleCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className CategoriesService
 * @description TODO
 * @date 2020-03-01 21:55
 */
@Service
public class CategoriesService {
    @Autowired
    private ArticleMapper articleMapper;
    public PageResult<ArticleCategoryDto> getDto(String tag,Integer pageSize,Integer pageNo) {
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
        List<ArticleCategoryDto> articleCategoryDtos=articleMapper.selectByTag(tag,pageSize,pageBegin);
        PageResult<ArticleCategoryDto> pageResult=new PageResult<>();
        pageResult.init(pageCount,pageNo);
        pageResult.setReslts(articleCategoryDtos);
        return pageResult;
    }
}
