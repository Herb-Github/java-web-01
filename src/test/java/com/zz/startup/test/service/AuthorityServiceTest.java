package com.zz.startup.test.service;

import com.zz.startup.entity.Authority;
import com.zz.startup.service.AuthorityService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class AuthorityServiceTest extends BaseServiceTest {

    @Autowired
    AuthorityService authorityService;

    @Test
    public void test_createAuthority() {
        Authority authority = new Authority();
        authority.setName("所有权限");
        authority.setPermission("*:*");
        authorityService.save(authority);
    }

    @Test
    public void test_query_authorities() {
        Set<Authority> userAuthorities = authorityService.queryUserAuthorities(1L);
        List<Authority> roleAuthorities = authorityService.queryRoleAuthorities(2L);

        System.out.println(userAuthorities);
        System.out.println(roleAuthorities);
    }
}
