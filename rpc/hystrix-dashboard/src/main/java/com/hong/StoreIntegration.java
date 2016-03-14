package com.hong;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

/**
 * Created by caihongwei on 16/3/14.
 */
@Component
public class StoreIntegration {
    @HystrixCommand(fallbackMethod = "defaultStores")
    public String getStores() {
        return "get stores success";
    }

    public String defaultStores() {
        return "get stores fail";
    }
}
