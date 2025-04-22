package com.kama.notes.service.impl;

import com.kama.notes.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 保存数据
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 设置过期时间
    public void setWithExpiry(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    // 获取数据
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除数据
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 判断键是否存在
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // 增加计数
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // 获取 Hash 值
    public Object getHashValue(String hashKey, String key) {
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    // 设置 Hash 值
    public void setHashValue(String hashKey, String key, Object value) {
        redisTemplate.opsForHash().put(hashKey, key, value);
    }
}
