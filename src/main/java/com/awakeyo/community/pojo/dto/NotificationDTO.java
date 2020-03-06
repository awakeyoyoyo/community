package com.awakeyo.community.pojo.dto;

import lombok.Data;

/**
 * @author awakeyoyoyo
 * @className NotificationDTO
 * @description TODO
 * @date 2020-01-30 16:32
 */
@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private String outerTitle;
    private String type;
    private Integer notifier;
    private Integer reciver;
    private Integer outerid;
    private String notifierName;
    private Integer typeId;

}
