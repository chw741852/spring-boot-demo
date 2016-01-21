package com.hong.demo.service;

import com.hong.VelocityApplication;
import com.hong.demo.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Created by caihongwei on 2016/1/21 16:20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(VelocityApplication.class)
@WebIntegrationTest("server.port:9000")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void tesRest() throws Exception {
        String s = restTemplate.getForObject(new URI("http://localhost:9000/api/supportCORS"), String.class);
        System.out.println(s);
    }

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setName("cai");
        user.setAge(27);
        int res = userService.save(user);
        assertEquals(1, res);
    }

    @Test
    public void testDelete() throws Exception {

    }
}