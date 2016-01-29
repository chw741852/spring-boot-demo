package com.hong;

import com.hong.demo.domain.User;
import com.hong.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private UserService userService;
    @Autowired
    private RedisOps redisOps;

    @RequestMapping("/")
    public String welcome(Model model) throws InterruptedException {
        model.addAttribute("time", new Date());
        model.addAttribute("message", this.message);
        redisOps.testConcurrent();
        return "welcome";
    }

    @RequestMapping("/notSupportCORS")
    @ResponseBody
    public String notSupportCORS() {
        return "This url not support CORS";
    }

    @RequestMapping("/api/supportCORS")
    @ResponseBody
    public String supportCORS() {
        return "This url support CORS";
    }

    @RequestMapping("/user/save")
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/";
    }
}
