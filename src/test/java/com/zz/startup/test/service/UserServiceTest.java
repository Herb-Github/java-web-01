package com.zz.startup.test.service;

import com.zz.startup.entity.User;
import com.zz.startup.repository.UserDao;
import com.zz.startup.service.UserService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Test
    public void test_deleteUser() {
        List<User> users = userService.findAll();
        userService.delete(users);
    }

    @Test
    public void test_createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin2");
        user.setPlainPassword("111111");

        userService.createUser(user);
    }

    @Test
    public void test_user_role() {
        int insert = userDao.insertUserRole(1L, 3L);
        int delete = userDao.deleteUserRole(1L, 3L);

        System.out.println(insert);
        System.out.println(delete);
    }
}
