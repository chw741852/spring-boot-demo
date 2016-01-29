package com.hong;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by caihongwei on 2016/1/29 18:03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VelocityApplication.class)
@WebAppConfiguration
@Ignore
public class RedisOpsTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Test
    public void test() {
        jedisConnectionFactory.setDatabase(1);
        stringRedisTemplate.opsForValue().set("foo", "bar", 100, TimeUnit.SECONDS);
    }
}