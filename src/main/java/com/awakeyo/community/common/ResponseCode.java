package com.awakeyo.community.common;

public enum ResponseCode {
    SUCCESS(200,"That's right"),
    ERROR(999,"That's Woring");
    private int code;
    private String desc;
    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
