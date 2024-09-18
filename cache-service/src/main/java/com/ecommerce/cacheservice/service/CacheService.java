package com.ecommerce.cacheservice.service;

import com.ecommerce.cacheservice.constant.CacheConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "ecommerce.cache.enabled", havingValue = "true")
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    private final RedisTemplate<String, Serializable> redisTemplate;

    @SuppressWarnings("unchecked")
    public Serializable getAllCache() {
        Map<String, Map<Object, Object>> allCaches = new HashMap<>();
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                ConcurrentMap<Object, Object> nativeCache = (ConcurrentMap<Object, Object>) cache.getNativeCache();
                Map<Object, Object> cacheContent = new HashMap<>(nativeCache);
                allCaches.put(cacheName, cacheContent);
            }
        }
        return (Serializable) allCaches;
    }


    @Cacheable(cacheNames = CacheConstant.PRODUCT_CACHE, key = "#p0")
    public Serializable putCache(String key, Serializable value) {
        return value;
    }

    public void clearAllCache() {
        this.cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(this.cacheManager.getCache(cacheName)).clear());
    }

    public Serializable getCache(String keyStr) {
        Set<String> keySet = this.redisTemplate.keys(CacheConstant.PRODUCT_CACHE + "*" + keyStr + "*");
        if (keySet != null && keySet.size() == 1) {
            return this.redisTemplate.opsForValue().get(keySet.iterator().next());
        }
        return null;
    }
}
