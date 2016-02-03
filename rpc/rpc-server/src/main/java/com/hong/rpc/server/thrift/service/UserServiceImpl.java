package com.hong.rpc.server.thrift.service;

import com.hong.rpc.api.thrift.model.User;
import com.hong.rpc.api.thrift.service.UserService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caihongwei on 2016/2/3 10:32.
 */
@Transactional
@Service
public class UserServiceImpl implements UserService.Iface {
    @Override
    public User getById(int id) throws TException {
        return null;
    }

    @Override
    public String update(User user) throws TException {
        return null;
    }

    @Override
    public String sayHello(String name) throws TException {
        return "Hello " + name;
    }
}
