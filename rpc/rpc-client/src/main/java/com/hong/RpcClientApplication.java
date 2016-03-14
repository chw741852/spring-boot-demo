package com.hong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by caihongwei on 2016/2/14 11:00.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RpcClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcClientApplication.class, args);
    }
}
