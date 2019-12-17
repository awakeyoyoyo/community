package com.awakeyo.community.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className QuestionDTO
 * @description TODO
 * @date 2019-12-04 23:06
 */
@Data
public class QuestionDTO {
    private Integer id;

    private String title;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private String decription;

    private User user;

    private List<CommentDTO> commentDTOs;

}
