package com.zz.startup.repository;

import com.zz.startup.entity.User;

import javax.persistence.Query;

public class UserDaoImpl extends NativeApiDao {

    public User findById(Long id) {
        Query query = em.createNativeQuery("select * from t_auth_user where id=?1", User.class);
        setParameters(query, id);
        return (User) query.getSingleResult();
    }
}
