package com.hong;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.util.Scanner;
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
     * 非线程安全，得上锁
     * @param key key
     * @param value value
     * @param timeout timeout
     * @param db db
     */
    public void setString(String key, String value, long timeout, int db) {
        jedisConnectionFactory.setDatabase(db);
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 线程安全，且serialize后，速度更快些
     * @param db
     * @param key
     * @param value
     * @param timeout
     */
    public void pipelineSetValue(final int db, final String key, final String value, final long timeout) {
        stringRedisTemplate.executePipelined(
            new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                    stringRedisConnection.select(db);
//                    byte[] _key = SerializationUtils.serialize(key);
//                    byte[] _value = SerializationUtils.serialize(value);
                    stringRedisConnection.set(key, value);
                    stringRedisConnection.expire(key, timeout);
                    System.err.println("change db to " + db);
                    System.err.println("current jedisConnectionFactory db is " + jedisConnectionFactory.getDatabase());
                    return null;
                }
            }
        );

    }

    /**
     * 测试并发
     */
    public void testConcurrent() {
//        MyThread t1 = new MyThread(1);
//        MyThread t2 = new MyThread(2);
        MyThread2 t1 = new MyThread2();
        MyThread2 t2 = new MyThread2();

        new Thread(t1).start();
        new Thread(t2).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private class MyThread2 implements Runnable {

        @Override
        public void run() {
            pipelineSetValue(1, "foo1", "bar1", 60);
            pipelineSetValue(2, "foo2", "bar2", 60);
        }
    }
}
