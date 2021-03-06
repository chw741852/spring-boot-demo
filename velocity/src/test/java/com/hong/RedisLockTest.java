package com.hong;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.cluster.lock.DistributedLock;
import org.springframework.cloud.cluster.redis.lock.RedisLockService;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Set;

/**
 * Created by caihongwei on 2016/2/3 19:35.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VelocityApplication.class)
@WebAppConfiguration
@Ignore
public class RedisLockTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Before
    public void setup() {
        cleanLocks();
    }

    @After
    public void close() {
        cleanLocks();
    }

    private void cleanLocks() {
        Set<String> keys = redisTemplate.keys(RedisLockService.DEFAULT_REGISTRY_KEY + ":*");
        redisTemplate.delete(keys);
    }

    @Test
    public void testSimpleLock() {
        RedisLockService lockService = new RedisLockService(connectionFactory);
        DistributedLock lock = lockService.obtain("lock");
        lock.lock();

        Set<String> keys = redisTemplate.keys(RedisLockService.DEFAULT_REGISTRY_KEY + ":*");
        assertThat(keys.size(), is(1));
        assertThat(keys.iterator().next(), is(RedisLockService.DEFAULT_REGISTRY_KEY + ":lock"));

        lock.unlock();
    }

    @Test
    public void testSecendLock() {
        new Thread(new MyThread("ONE")).start();
        new Thread(new MyThread("TWO")).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class MyThread implements Runnable {
        private final String name;
        private final RedisLockService lockService;
        private final DistributedLock lock;

        private MyThread(String name) {
            this.name = name;
            lockService = new RedisLockService(connectionFactory);
            lock = lockService.obtain("lock");
        }

        @Override
        public void run() {
//            try {
//                Thread.sleep(100);
//                System.err.println("I'm " + name);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            if (lock.tryLock()) {
                try {
                    lock.lock();
                    Thread.sleep(100);
                    System.err.println("I'm " + name);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
