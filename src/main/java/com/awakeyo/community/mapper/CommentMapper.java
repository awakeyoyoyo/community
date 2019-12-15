package com.awakeyo.community.mapper;

import com.awakeyo.community.pojo.Comment;
import com.awakeyo.community.pojo.Reply;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectByTopId(Integer topId);


}