package com.zheng.blog.service.impl;

import com.zheng.blog.dao.UserRepository;
import com.zheng.blog.po.User;
import com.zheng.blog.service.UserService;
import com.zheng.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
