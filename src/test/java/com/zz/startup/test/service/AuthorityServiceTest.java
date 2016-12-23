package com.zz.startup.test.service;

import com.zz.startup.entity.Authority;
import com.zz.startup.service.AuthorityService;
import com.zz.startup.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthorityServiceTest extends BaseServiceTest {

    @Autowired
    AuthorityService authorityService;

    @Test
    public void test_createAuthority() {
        Authority authority = new Authority();
        authority.setName("用户");
        authority.setPermission("user:*");
        authorityService.save(authority);
    }
}
