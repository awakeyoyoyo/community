package com.awakeyo.community.exception;

public enum  QuestionErrorCode {
    QUESTION_NOT_FOUND("这个问题不存在噢！！！");
    private String message;

    public String getMessage() {
        return message;
    }
    QuestionErrorCode(String message){
        this.message=message;
    }
}
