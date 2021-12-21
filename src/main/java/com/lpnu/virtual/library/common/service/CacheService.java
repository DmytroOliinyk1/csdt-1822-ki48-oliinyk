package com.lpnu.virtual.library.common.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private Cache<String, List<Long>> searchIds = CacheBuilder.newBuilder()
            .weakValues()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(512)
            .build();

    public List<Long> getFromSearchIds(String searchId) {
        return searchIds.asMap().get(searchId);
    }

        public void putInSearchIds(String searchId, List<Long> assetIds) {
        searchIds.put(searchId, assetIds);
    }

    public Boolean containsInSearchIds(String searchId) {
        return searchIds.asMap().containsKey(searchId);
    }
}
