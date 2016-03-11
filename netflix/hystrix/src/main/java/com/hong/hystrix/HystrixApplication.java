package com.hong.hystrix;

import com.hong.hystrix.basic.CommandHelloWorld;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by caihongwei on 16/3/11.
 */
@SpringBootApplication
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
        new CommandHelloWorld("caihongwei").execute();
    }

    @Bean
    public ServletRegistrationBean hystrixRegistration(HystrixMetricsStreamServlet metricsStreamServlet) {
        return new ServletRegistrationBean(metricsStreamServlet, "/hystrix.stream");
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet() {
        return new HystrixMetricsStreamServlet();
    }
}
