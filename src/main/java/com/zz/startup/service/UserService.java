package com.zz.startup.service;

import com.zz.startup.entity.User;
import com.zz.startup.repository.UserDao;
import com.zz.startup.util.Constants;
import com.zz.startup.util.Digests;
import com.zz.startup.util.Encodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<User, Long> {

    @Autowired
    private UserDao userDao;

    public void createUser(User user) {
        entryptPassword(user);
        userDao.save(user);
    }

    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(Constants.SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, Constants.HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

}
