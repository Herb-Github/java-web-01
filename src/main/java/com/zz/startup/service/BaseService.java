package com.zz.startup.service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.zz.startup.entity.BaseEntity;
import com.zz.startup.repository.BaseDao;
import com.zz.startup.util.DynamicSpecifications;
import com.zz.startup.util.Reflections;
import com.zz.startup.util.SearchFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.*;

public abstract class BaseService<M extends BaseEntity, ID extends Serializable> {

    @Autowired
    protected BaseDao<M, ID> baseDao;

    protected final Class<M> entityClass;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    protected BaseService() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    public M get(ID id) {
        return baseDao.findOne(id);
    }

    public List<M> get(Collection<ID> ids) {
        Iterable<M> elements = baseDao.findAll(ids);
        List<M> models = new ArrayList<>();
        Iterators.addAll(models, elements.iterator());
        return models;
    }

    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable) {
        Specification<M> query = buildQuery(filters);

        long count = baseDao.count(query);
        List<M> list = baseDao.findAll(query);
        return new PageImpl(list, pageable, count);
    }

    public List<M> findBy(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        SearchFilter sf = new SearchFilter(key, operator, value);
        filters.put(key, sf);
        return findBySearchFilter(filters);
    }

    public M findOne(String key, SearchFilter.Operator operator, Object value) {
        List<M> list = findBy(key, operator, value);
        if (!list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    public Page<M> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    public List<M> findAll() {
        return baseDao.findAll();
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

    public Collection<M> save(Collection<M> entites) {
        return baseDao.save(entites);
    }

    public M update(ID id, M entity) {
        M existM = get(id);
        copyNonNullProperties(entity, existM);
        baseDao.save(existM);
        return existM;
    }

    public void delete(M entity) {
        baseDao.delete(entity);
    }

    public void delete(ID id) {
        baseDao.delete(id);
    }

    public void delete(Collection<M> entitys) {
        baseDao.delete(entitys);
    }

    protected Specification<M> buildQuery(Map<String, SearchFilter> filters) {
        return DynamicSpecifications.bySearchFilter(filters.values());
    }

    public List<M> nativeQuery(String sql, Object ...params) {
        Query query = entityManagerFactory.createEntityManager().createNativeQuery(sql);
        return query.getResultList();
    }

    public List<M> findBySearchFilter(Map<String, SearchFilter> filters) {
        Specification<M> query = buildQuery(filters);
        return baseDao.findAll(query);
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
