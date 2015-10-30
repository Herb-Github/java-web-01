package com.zz.startup.service;

import com.zz.startup.repository.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.Collection;

public abstract class BaseService <M, ID extends Serializable> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected BaseDao<M, ID> baseDao;

    public M get(ID id) {
        return baseDao.findOne(id);
    }

    public Long getTotalCount() {
        return baseDao.count();
    }

    public boolean isExist(ID id) {
        return baseDao.exists(id);
    }

    public M save(M entity) {
        return baseDao.save(entity);
    }

    public void insertAll(Collection<M> entites) {
        mongoTemplate.insertAll(entites);
    }

    public void delete(M entity) {
        baseDao.delete(entity);
    }

    public void delete(ID id) {
        baseDao.delete(id);
    }
}
