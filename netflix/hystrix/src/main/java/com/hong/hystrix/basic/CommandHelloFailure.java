package com.hong.hystrix.basic;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by caihongwei on 16/3/7.
 *
 * hystrix 处理错误 getFailure
 */
public class CommandHelloFailure extends HystrixCommand<String> {
    private final String name;

    public CommandHelloFailure(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }


    @Override
    protected String run() throws Exception {
        throw new RuntimeException("This command always fails...");
    }

    @Override
    protected String getFallback() {
        return "Hello Failure " + name + "!";
    }
}
