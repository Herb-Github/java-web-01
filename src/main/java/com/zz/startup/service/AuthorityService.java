package com.zz.startup.service;

import com.zz.startup.entity.Authority;
import com.zz.startup.repository.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AuthorityService extends BaseService<Authority, Long> {

    @Autowired
    private AuthorityDao authorityDao;

    public Set<Authority> queryUserAuthorities(Long userId){
        return authorityDao.queryUserAuthorities(userId);
    }

    public List<Authority> queryRoleAuthorities(Long roleId) {
        return authorityDao.queryRoleAuthorities(roleId);
    }
}
