package com.yang.service;

import com.yang.dao.UserMapper;
import com.yang.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:Yang
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Transactional
    public int add(User user){
        return userMapper.insert(user);
    }
}
