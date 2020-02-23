package com.awakeyo.community.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author awakeyoyoyo
 * @className ServerResponse
 * @description TODO
 * @date 2019-12-15 15:24
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)//即没有初始化的成员变量 key也会消失，不返回前端
public class WebResponse<T> {
    private  int status;
    private String msg;
    private T data;

    private WebResponse(int status){
        this.status=status;
    }
    private WebResponse(int status, T data){
        this.status=status;
        this.data=data;
    }
    private WebResponse(int status, T data, String msg){
        this.status=status;
        this.data=data;
        this.msg=msg;
    }
    private WebResponse(int status, String msg){
        this.status=status;
        this.msg=msg;
    }
    //JSON返回前端的时候这个忽略
    @JsonIgnore
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }
    public  int getStatus(){
        return  status;
    }
    public  T getData(){
        return  data;
    }
    public String getMsg() {
        return msg;
    }
    public static <T> WebResponse<T> createBySuccess(){
        return  new WebResponse<T>(ResponseCode.SUCCESS.getCode());
    }
    public static <T> WebResponse<T> createBySuccessMessage(String msg){
        return new WebResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> WebResponse<T> createBySuccess(T data){
        return new WebResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> WebResponse<T> createBySuccess(String msg, T data){
        return new WebResponse<T>(ResponseCode.SUCCESS.getCode(),data,msg);
    }
    public static <T> WebResponse<T> createByError(){
        return  new WebResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> WebResponse<T> createByErrorMessage(String errorMsg){
        return new WebResponse<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }
    public static <T> WebResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage){
        return new WebResponse<T>(errorCode,errorMessage);
    }
}
