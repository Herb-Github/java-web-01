package com.zz.startup.test.service;

import com.zz.startup.entity.Role;
import com.zz.startup.service.RoleService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceTest extends BaseServiceTest {

    @Autowired
    RoleService roleService;

    @Test
    public void test_createRole() {
        Role role = new Role();
        roleService.save(role);

        role = new Role();
        roleService.save(role);
    }
}
