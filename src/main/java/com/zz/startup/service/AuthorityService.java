package com.zz.startup.service;

import com.zz.startup.entity.Authority;
import com.zz.startup.repository.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService extends BaseService<Authority, Long> {

    @Autowired
    private AuthorityDao authorityDao;

}
