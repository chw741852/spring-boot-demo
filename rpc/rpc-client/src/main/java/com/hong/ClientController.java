package com.hong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caihongwei on 2016/2/14 11:35.
 */
@RestController
@RefreshScope
public class ClientController {
    @Value("${client.foo:default}")
    private String foo;

    @Value("${user.password:default}")
    private String password;

    @Autowired
    private ConfigurationPropertiesConfig propertiesConfig;

    @RequestMapping("/foo")
    public String foo() {
        return foo;
    }

    @RequestMapping("/cipher")
    public String cipher() {
        return password;
    }

    @RequestMapping("/user")
    public String user() {
        return propertiesConfig.getUsername();
    }
}
