package com.zz.startup.test.service;

import com.zz.startup.entity.User;
import com.zz.startup.service.UserService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void test_deleteUser() {
        List<User> users = userService.findAll();
        userService.delete(users);
    }

    @After
    public void test_createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPlainPassword("111111");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        userService.createUser(user);
    }
}
