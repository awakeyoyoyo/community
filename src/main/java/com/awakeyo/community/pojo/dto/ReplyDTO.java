package com.awakeyo.community.pojo.dto;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className ReplyDTO
 * @description TODO
 * @date 2019-12-15 16:16
 */
@Data
public class ReplyDTO {
    private Long id;

    private Long commentId;

    private String formUid;

    private String toUid;

    private Long gmtCreate;

    private String content;

    private Integer replyLike;

    private User fromUser;

    private User toUser;
}
