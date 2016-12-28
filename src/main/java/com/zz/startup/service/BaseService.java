package com.zz.startup.service;

import com.google.common.collect.Lists;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseService<M extends BaseEntity, ID extends Serializable> {

    @Autowired
    protected BaseDao<M, ID> baseDao;

    protected final Class<M> entityClass;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    protected BaseService() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    public M find(ID id) {
        return baseDao.findOne(id);
    }

    public List<M> find(Collection<ID> ids) {
        Iterable<M> elements = baseDao.findAll(ids);
        return Lists.newArrayList(elements);
    }

    public M findOne(String key, Object value) {
        return findOne(key, SearchFilter.Operator.EQ, value);
    }

    public M findOne(String key, SearchFilter.Operator operator, Object value) {
        return findOne(new SearchFilter(key, operator, value));
    }

    public M findOne(SearchFilter filter) {
        List<SearchFilter> filters = Lists.newArrayList(filter);
        Specification<M> spec = buildQuery(filters);
        return baseDao.findOne(spec);
    }

    public List<M> findAll() {
        return baseDao.findAll();
    }

    public Page<M> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    public Long totalCount() {
        return baseDao.count();
    }

    public Page<M> findPage(SearchFilter filter, int pageSize, int pageNo) {
        List<SearchFilter> filters = Lists.newArrayList(filter);
        Pageable pageable = new PageRequest(pageNo, pageSize);
        return findPage(filters, pageable);
    }

    public Page<M> findPage(List<SearchFilter> filters, Pageable pageable) {
        return findPage(filters, pageable, null);
    }

    public Page<M> findPage(List<SearchFilter> filters, Pageable pageable, Sort sort) {
        PageRequest newPage = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Specification<M> spec = buildQuery(filters);
        return baseDao.findAll(spec, newPage);
    }

    public List<M> findBy(String key, Object value) {
        return findBy(key, SearchFilter.Operator.EQ, value);
    }

    public List<M> findBy(String key, SearchFilter.Operator operator, Object value) {
        return findBy(new SearchFilter(key, operator, value));
    }

    public List<M> findBy(SearchFilter filter) {
        List<SearchFilter> filters = Lists.newArrayList(filter);
        return findBy(filters);
    }

    public List<M> findBy(List<SearchFilter> filters) {
        return findBy(filters, null);
    }

    public List<M> findBy(List<SearchFilter> filters, Sort sort) {
        Specification<M> spec = buildQuery(filters);
        return baseDao.findAll(spec, sort);
    }

    public boolean exists(ID id) {
        return baseDao.exists(id);
    }

    public M save(M entity) {
        return baseDao.save(entity);
    }

    public Collection<M> save(Collection<M> entities) {
        return baseDao.save(entities);
    }

    public M update(ID id, M entity) {
        M existM = find(id);
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

    public void deleteBatch(Collection<M> entities) {
        baseDao.deleteInBatch(entities);
    }

    protected Specification<M> buildQuery(List<SearchFilter> filters) {
        return DynamicSpecifications.bySearchFilter(filters);
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
