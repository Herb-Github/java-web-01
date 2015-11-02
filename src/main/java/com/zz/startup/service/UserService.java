package com.zz.startup.service;

import com.zz.startup.entity.User;
import com.zz.startup.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService extends BaseService<User, String> {

    @Autowired
    private UserDao userDao;
}
