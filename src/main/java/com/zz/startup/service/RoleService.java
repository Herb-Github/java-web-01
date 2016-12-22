package com.zz.startup.service;

import com.zz.startup.entity.Role;
import com.zz.startup.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, Long> {

    @Autowired
    private RoleDao roleDao;
}
