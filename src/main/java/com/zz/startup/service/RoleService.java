package com.zz.startup.service;

import com.zz.startup.entity.Role;
import com.zz.startup.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends BaseService<Role, Long> {

    @Autowired
    private RoleDao roleDao;

    public List<Role> queryUserRoles(Long userId) {
        return roleDao.queryUserRoles(userId);
    }
}
