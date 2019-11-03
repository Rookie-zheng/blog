package com.zheng.blog.service;

import com.zheng.blog.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
