package com.awakeyo.community.exception;

/**
 * @author awakeyoyoyo
 * @className RedisException
 * @description TODO
 * @date 2020-02-26 11:43
 */
public class RedisException extends RuntimeException{
    public RedisException(String message){
        super(message);
    }
}
