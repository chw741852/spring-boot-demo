package com.hong;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by caihongwei on 16/3/1.
 */
@RefreshScope
@Component
@Configuration
@ConfigurationProperties(prefix = "user")
public class ConfigurationPropertiesConfig {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
