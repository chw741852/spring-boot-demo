package com.hong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caihongwei on 2016/2/5 16:33.
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class RpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }

    @Value("${info.description:local info}")
    private String info;

    @RequestMapping("/hello/{name}")
    public String Hello(@PathVariable String name) {
        return "Hello " + name;
    }

    @RequestMapping("/")
    public String home() {
        return info;
    }
}
