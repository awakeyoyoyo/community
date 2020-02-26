package com.awakeyo.community.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @author awakeyoyoyo
 * @className RedisUtil
 * @description TODO
 * @date 2020-02-24 21:40
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    enum Position {
        BEFORE, AFTER
    }
    /**
     * 实现命令 : SET key value
     * 添加一个持久化的 String 类型的键值对
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    /**
     * 实现命令 : SET key value EX 秒、 setex  key value 秒
     * 添加一个 String 类型的键值对，并设置生存时间
     *
     * @param key
     * @param value
     * @param ttl   key 的生存时间，单位:秒
     */
    public void set(String key, Object value, int ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }
    /**
     * 实现命令 : GET key
     * 获取一个key的value
     *
     * @param key
     * @return value
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 实现命令 : DELETE key
     * 删除一个key&value
     *
     * @param key
     * @return boolean
     */
    public  boolean del(final String key) {

        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }
}
