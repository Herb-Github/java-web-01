package com.zz.startup.test;

import com.google.common.collect.Lists;
import com.zz.startup.entity.User;
import com.zz.startup.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-config.xml")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("yxb@yxb.com");
        user.setPlainPassword("123456");
        user.setUserName("yxb");
        user.setPermissions(Lists.newArrayList("*:*"));

        userService.createUser(user);
    }
}
