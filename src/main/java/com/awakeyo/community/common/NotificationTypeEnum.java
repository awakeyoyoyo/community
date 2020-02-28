package com.awakeyo.community.common;

/**
 * @author awakeyoyoyo
 * @className NotificationEnum
 * @description TODO
 * @date 2020-01-28 18:28
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了您问题"),
    REPLY_BLOG(3,"回复了您博客"),
    REPLY_COMMENT(2,"回复了您");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
