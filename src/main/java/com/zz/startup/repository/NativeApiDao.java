package com.zz.startup.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public abstract class NativeApiDao {

    @PersistenceContext
    protected EntityManager em;

    public <M> M findOne(String jpql, Object... params) {
        Query query = em.createQuery(jpql);
        setParameters(query, params);
        query.setMaxResults(1);
        List<M> result = query.getResultList();

        if (result.size() > 0) {
            return result.get(0);
        }

        return null;
    }

    public <T> List<T> find(String nativeSql, Class<T> entityClass, Object... params) {
        Query query = em.createNativeQuery(nativeSql, entityClass);
        setParameters(query, params);
        return query.getResultList();
    }

    public int batchUpdate(String jpql, Object... params) {
        Query query = em.createQuery(jpql);
        setParameters(query, params);
        return query.executeUpdate();
    }

    protected void setParameters(Query query, Object... params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
    }
}
