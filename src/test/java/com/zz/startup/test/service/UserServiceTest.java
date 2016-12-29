package com.zz.startup.test.service;

import com.zz.startup.entity.User;
import com.zz.startup.repository.UserDao;
import com.zz.startup.service.UserService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Test
    public void test_deleteUser() {
        List<User> users = userService.findAll();
        userService.deleteBatch(users);
    }

    @Test
    public void test_createUser() {
        User user = new User();
        user.setUsername("admin2");
        user.setPlainPassword("111111");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        userService.createUser(user);
    }

    @Test
    public void test_user_role() {
        int insert = userDao.insertUserRole(1L, 3L);
        int delete = userDao.deleteRole(1L, 3L);

        System.out.println(insert);
        System.out.println(delete);
    }

    @Test
    public void test_native_api() {
        User user = userDao.findById(1L);
        System.out.println(user);
    }
}
