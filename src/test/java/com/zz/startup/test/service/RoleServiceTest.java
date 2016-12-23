package com.zz.startup.test.service;

import com.zz.startup.entity.Role;
import com.zz.startup.repository.RoleDao;
import com.zz.startup.service.RoleService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class RoleServiceTest extends BaseServiceTest {

    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;

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

    @Test
    public void test_role_authority() {
        int insert = roleDao.insertRoleAuthority(1L, 6L);
        int delete = roleDao.deleteRoleAuthority(1L, 6L);

        System.out.println(insert);
        System.out.println(delete);
    }
}
