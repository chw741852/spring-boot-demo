package com.hong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by caihongwei on 2016/1/29 17:02.
 */
@Component
public class RedisOps {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * string 类型
     * @param key key
     * @param value value
     * @param timeout timeout
     * @param db db
     */
    public void setString(String key, String value, long timeout, int db) {
        if (jedisConnectionFactory.getDatabase() != db)
            jedisConnectionFactory.setDatabase(db);
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
}
