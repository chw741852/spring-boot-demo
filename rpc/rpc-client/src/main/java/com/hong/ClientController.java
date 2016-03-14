package com.hong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

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

    @RequestMapping("/hello")
    public String hello() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        // FIXME Wrong!
        return restTemplate.getForObject(new URI("http://rpcserver:8080/hello/from-test"), String.class);
    }
}
