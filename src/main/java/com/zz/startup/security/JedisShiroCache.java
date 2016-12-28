package com.zz.startup.security;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

@SuppressWarnings("unchecked")
public class JedisShiroCache<K, V> implements Cache<K, V> {

    private static final String SHIRO_CACHE_REDIS_PREFIX = "SHIRO_CACHE_";
    private static Logger logger = LoggerFactory.getLogger(JedisShiroCache.class);

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    private String name;

    public JedisShiroCache() {
    }

    public JedisShiroCache(String name) {
        this.name = name;
    }

    public V get(K key) throws CacheException {
        try {
            return (V) redisTemplate.opsForHash().get((K) SHIRO_CACHE_REDIS_PREFIX, getCacheKey(key));
        } catch (Exception e) {
            logger.warn("find cache key {} null", key);
            return null;
        }
    }

    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        redisTemplate.opsForHash().put((K) SHIRO_CACHE_REDIS_PREFIX, getCacheKey(key), value);
        return previos;
    }

    public V remove(K key) throws CacheException {
        V previos = get(key);
        redisTemplate.opsForHash().delete((K) SHIRO_CACHE_REDIS_PREFIX, getCacheKey(key));
        return previos;
    }

    public void clear() throws CacheException {
        redisTemplate.delete((K) SHIRO_CACHE_REDIS_PREFIX);
    }

    public int size() {
        return redisTemplate.opsForHash().size((K) SHIRO_CACHE_REDIS_PREFIX).intValue();
    }

    public Set<K> keys() {
        return (Set<K>) redisTemplate.opsForHash().keys((K) SHIRO_CACHE_REDIS_PREFIX);
    }

    public Collection<V> values() {
        return (Collection<V>) redisTemplate.opsForHash().values((K) SHIRO_CACHE_REDIS_PREFIX);
    }

    private String getCacheKey(Object key) {
        return getName() + ":" + key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
