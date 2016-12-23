package com.zz.startup.test.service;

import com.zz.startup.entity.Role;
import com.zz.startup.service.RoleService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class RoleServiceTest extends BaseServiceTest {

    @Autowired
    RoleService roleService;

    @Test
    public void test_createRole() {
        Role role = new Role();
        role.setName("superadmin");
        role.setSummary("超级管理员");
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());

        roleService.save(role);
    }

    @Test
    public void test_query_user_roles() {
        List<Role> roles = roleService.queryUserRoles(1L);
        System.out.println(roles.size());
    }
}
