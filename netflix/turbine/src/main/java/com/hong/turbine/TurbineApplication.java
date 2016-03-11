package com.hong.turbine;

import com.netflix.turbine.init.TurbineInit;
import com.netflix.turbine.streaming.servlet.TurbineStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by caihongwei on 16/3/11.
 */
@SpringBootApplication
public class TurbineApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
        TurbineInit.init();
    }

    @Bean
    public ServletRegistrationBean TurbineRegistration(TurbineStreamServlet turbineStreamServlet) {
        return new ServletRegistrationBean(turbineStreamServlet, "/turbine.stream");
    }

    @Bean
    public TurbineStreamServlet turbineStreamServlet() {
        return new TurbineStreamServlet();
    }
}
