package com.zz.startup.test;

import com.google.common.collect.Lists;
import com.zz.startup.entity.User;
import com.zz.startup.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springside.modules.persistence.SearchFilter;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-config.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    public void test_createUser() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setCreationTime(new Date());
            user.setEmail("yxb" + i + "@admin.com");
            user.setPlainPassword("111111");
            user.setUserName("yxb" + i);
            user.setPermissions(Lists.newArrayList("*:*"));

            userService.createUser(user);
        }
    }

    @Test
    public void test_deleteUser(){
        List<User> users = userService.findBy("userName", SearchFilter.Operator.LIKE, "yxb");
        userService.delete(users);
    }
}
