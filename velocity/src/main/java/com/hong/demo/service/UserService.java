package com.hong.demo.service;

import com.hong.demo.domain.User;
import com.hong.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by caihongwei on 2016/1/21 16:15.
 */
@Transactional
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int save(User user) {
        return userMapper.insert(user);
    }

    public int delete(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }
}
