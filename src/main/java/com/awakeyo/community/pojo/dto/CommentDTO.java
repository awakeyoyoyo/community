package com.awakeyo.community.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author awakeyoyoyo
 * @className ComentDTO
 * @description TODO
 * @date 2019-12-15 14:57
 */
@Data
public class CommentDTO {
    private Long id;

    private Integer topicId;

    private String content;

    private String fromUid;

    private Long gmtCreate;

    private Integer commentLike;

    private User fromUser;

    private List<ReplyDTO> replyDTOS;
}
