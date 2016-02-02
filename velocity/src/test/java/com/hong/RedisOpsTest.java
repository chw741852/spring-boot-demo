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
import org.springframework.util.StopWatch;

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
    @Autowired
    private RedisOps redisOps;

    private final StopWatch stopWatch = new StopWatch();

    @Test
    public void testConcurrent() {
//        jedisConnectionFactory.setDatabase(2);
//        stringRedisTemplate.opsForValue().set("foo", "bar", 100, TimeUnit.SECONDS);

        redisOps.testConcurrent();
    }

    @Test
    public void testSetString() {
        stopWatch.start();
        redisOps.setString("foo1", "bar1", 600, 1);
        redisOps.setString("foo2", "bar2", 600, 2);
        stopWatch.stop();
        System.out.println("一共耗时：" + stopWatch.getTotalTimeSeconds());
    }
}