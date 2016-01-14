package com.hong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by caihongwei on 2016/1/11 19:04.
 */
@Controller
public class WelcomeController {
    @Value("${application.message:Hello World}")
    private String message;

    @RequestMapping("/")
    public String welcome(Model model) throws InterruptedException {
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.message);
        return "welcome";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "The Test Page!";
    }
}
