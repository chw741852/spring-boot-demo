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
 * TODO
 * 1、@RefreshScope起到什么作用？
 * 2、远程配置文件修改后，这里不会自动刷新。
 */
@RestController
@RefreshScope
public class ClientController {
    @Value("${foo:default}")
    private String foo;

    @Autowired
    private ConfigurationPropertiesConfig propertiesConfig;

    @RequestMapping("/")
    public String home() {
        return foo;
    }

    @RequestMapping("/user")
    public String user() {
        return propertiesConfig.getUsername();
    }
}
