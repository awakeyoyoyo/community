package com.awakeyo.community.pojo.dto;

import com.awakeyo.community.pojo.User;
import lombok.Data;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className ArticleDto
 * @description TODO
 * @date 2020-02-27 21:02
 */
@Data
public class ArticleDto {
    private Integer id;

    private Long gmtCreate;

    private Long gtmModifiled;

    private Integer creator;

    private String title;

    private String decription;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private Integer commentCount;

    private String content;

    private User user;

    private List<CommentDTO> commentDTOs;
}
