package com.zz.startup.service;

import com.zz.startup.entity.Role;
import com.zz.startup.entity.User;
import com.zz.startup.repository.RoleDao;
import com.zz.startup.repository.UserDao;
import com.zz.startup.util.Constants;
import com.zz.startup.util.Digests;
import com.zz.startup.util.Encodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService<User, Long> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public void createUser(User user) {
        entryptPassword(user);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userDao.save(user);
    }

    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, Constants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    public void updateUserRoles(Long id, Long[] roleIds) {
        userDao.deleteRoles(id);
        for (Long roleId : roleIds) {
            userDao.insertUserRole(id, roleId);
        }
    }

    public void deleteUser(Long id) {
        List<Role> roles = roleDao.queryUserRoles(id);
        roles.forEach(role -> roleDao.deleteAuthorities(role.getId()));
        userDao.delete(id);
        userDao.deleteRoles(id);
    }
}
