package com.hong.hystrix.basic;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caihongwei on 16/3/7.
 */
public class CommandUsingRequestCacheTest {
    @Test
    public void testWithoutCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            assertTrue(new CommandUsingRequestCache(2).execute());
            assertFalse(new CommandUsingRequestCache(1).execute());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testWithCacheHits() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache a2 = new CommandUsingRequestCache(2);
            CommandUsingRequestCache b2 = new CommandUsingRequestCache(2);

            assertTrue(a2.execute());
            // this is the first time we've executed this command with the value of "2" so it should not be from cache
            assertFalse(a2.isResponseFromCache());

            assertTrue(b2.execute());
            // this is the second time we've executed this command with the same value so it should return from cache
            assertTrue(b2.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        // start a new request context
        context = HystrixRequestContext.initializeContext();
        try {
            CommandUsingRequestCache c2 = new CommandUsingRequestCache(2);
            assertTrue(c2.execute());
            // this is a new request context so this should not come from cache
            assertFalse(c2.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }
}