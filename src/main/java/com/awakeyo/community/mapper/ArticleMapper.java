package com.awakeyo.community.mapper;

import com.awakeyo.community.pojo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    int updateViewCount(Integer id);

    Integer selectAllSearch(String search);

    List<Article> selectListSearch(@Param("search") String search, @Param("pageBegin") Integer pageBegin, @Param("pageSize") Integer pageSize);

    Integer selectAll();

    List<Article> selectList(@Param("pageBegin") Integer pageBegin, @Param("pageSize") Integer pageSize);
}