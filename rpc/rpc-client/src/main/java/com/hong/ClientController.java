package com.hong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by caihongwei on 2016/2/14 11:35.
 * TODO
 * 1、@RefreshScope起到什么作用？
 * 2、远程配置文件修改后，这里不会自动刷新。
 */
@Controller
@RefreshScope
public class ClientController {
    @Value("${user.username:default}")
    private String username;

    @Value("${name:myname}")
    private String name;

    @Value("${foo:default}")
    private String foo;

    @Value("${info.url:default}")
    private String url;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("username", username);
        modelMap.addAttribute("remote", name);
        modelMap.addAttribute("foo", foo);
        modelMap.addAttribute("url", url);
        return "index";
    }
}
