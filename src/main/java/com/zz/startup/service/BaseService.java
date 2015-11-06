package com.zz.startup.service;

import com.google.common.collect.Maps;
import com.zz.startup.repository.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.utils.Reflections;

import java.io.Serializable;
import java.util.*;

public abstract class BaseService<M, ID extends Serializable> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Autowired
    protected BaseDao<M, ID> baseDao;

    public M get(ID id) {
        return baseDao.findOne(id);
    }

    public Page<M> findPage(Map<String, SearchFilter> filters, Pageable pageable) {
        Query query = buildQuery(filters);
        Class<M> clazz = Reflections.getClassGenricType(getClass());
        long count = mongoTemplate.count(query, clazz);
        query.with(pageable);
        query.with(new Sort(Sort.Direction.DESC, "modifiedTime"));
        return new PageImpl(mongoTemplate.find(query, clazz), pageable, count);
    }

    public List<M> findBy(String key, SearchFilter.Operator operator, Object value) {
        Map<String, SearchFilter> filters = Maps.newHashMap();
        SearchFilter sf = new SearchFilter(key, operator, value);
        filters.put(key, sf);
        return findBySearchFilter(filters);
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

    public M update(ID id, M m) {
        M existM = get(id);
        copyNonNullProperties(m, existM);
        baseDao.save(existM);
        return existM;
    }

    public void delete(M entity) {
        baseDao.delete(entity);
    }

    public void delete(ID id) {
        baseDao.delete(id);
    }

    protected Query buildQuery(Map<String, SearchFilter> filters) {
        Query query = new Query();
        for (Map.Entry<String, SearchFilter> filter : filters.entrySet()) {
            SearchFilter sf = filter.getValue();
            String fieldName = sf.fieldName;
            Object value = sf.value;

            Criteria criteria = Criteria.where(fieldName);
            switch (sf.operator) {
                case EQ:
                    criteria.is(value);
                    break;
                case GT:
                    criteria.gt(value);
                    break;
                case LT:
                    criteria.lt(value);
                    break;
                case GTE:
                    criteria.gte(value);
                    break;
                case LTE:
                    criteria.lte(value);
                    break;
                case LIKE:
                    criteria.regex((String) value, "i");
                    break;
                default:
                    break;
            }
            query.addCriteria(criteria);
        }
        return query;
    }

    public List<M> findBySearchFilter(Map<String, SearchFilter> filters) {
        Class<M> clazz = Reflections.getClassGenricType(getClass());
        Query query = buildQuery(filters);
        return mongoTemplate.find(query, clazz);
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
