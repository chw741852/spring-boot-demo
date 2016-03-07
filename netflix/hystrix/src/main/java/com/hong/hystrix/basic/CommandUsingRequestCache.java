package com.hong.hystrix.basic;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by caihongwei on 16/3/7.
 *
 * hystrix 使用缓存
 */
public class CommandUsingRequestCache extends HystrixCommand<Boolean> {
    private final int value;

    public CommandUsingRequestCache(int value) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.value = value;
    }

    @Override
    protected Boolean run() throws Exception {
        return value == 0 || value % 2 == 0;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(value);
    }
}
