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
    public synchronized void setString(String key, String value, long timeout, int db) {
        jedisConnectionFactory.setDatabase(db);
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 这里存在并发问题
     */
    public void testConcurrent() {
        MyThread t1 = new MyThread(1);
        MyThread t2 = new MyThread(2);
        new Thread(t1).start();
        new Thread(t2).start();
    }

    private class MyThread implements Runnable {
        private final int db;

        private MyThread(int db) {
            this.db = db;
        }

        @Override
        public void run() {
            try {
                synchronized (RedisOps.class) {
                    jedisConnectionFactory.setDatabase(db);
                    Thread.sleep(100);
                    stringRedisTemplate.opsForValue().set("foo" + db, "bar", 600, TimeUnit.SECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
