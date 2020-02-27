package com.awakeyo.community.mapper;

import com.awakeyo.community.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    Integer selectQuestionId(Long commentId);

    List<Comment> selectByTopIdType(@Param("topId") Integer topId, @Param("type") String type);
}