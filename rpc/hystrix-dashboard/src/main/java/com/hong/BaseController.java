package com.hong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by caihongwei on 16/3/14.
 */
@RestController
public class BaseController {
    @Autowired
    private StoreIntegration storeIntegration;

    @RequestMapping("/init")
    public String init() {
        return storeIntegration.getStores();
    }
}
