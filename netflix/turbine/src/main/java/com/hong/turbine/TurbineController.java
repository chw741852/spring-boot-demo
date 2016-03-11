package com.hong.turbine;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by caihongwei on 16/3/11.
 */
@Controller
public class TurbineController {
    @RequestMapping("/")
    public String home() {

        return "index";
    }
}
