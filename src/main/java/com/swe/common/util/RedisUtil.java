package com.swe.common.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @Author: cbw
 * @Description: Redis配置类
 */

public class RedisUtil {
    private static final int DEFAULT_EXPIRE_TIME = 60 * 1000;

    private static RedisTemplate<String, Object> redisTemplate=ApplicationUtil.getBean("redisTemplate");;

    /**
     * 设置键值对，使用默认过期时间
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, String value) {
        set(key, value, DEFAULT_EXPIRE_TIME);
    }

    /**
     * 设置键值对，指定过期时间
     *
     * @param key        key
     * @param value      value
     * @param expireTime 过期时间
     */
    public static void set(String key, String value, long expireTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
    }
    /**
     * 获取 value
     * @param key key
     */
    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key
     *
     * @param key key
     */
    public static void delete(String key) {
        redisTemplate.delete(key);
    }
}
