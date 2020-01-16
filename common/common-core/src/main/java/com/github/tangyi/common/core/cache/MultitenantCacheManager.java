package com.github.tangyi.common.core.cache;

import com.github.tangyi.common.core.utils.SysUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Map;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月16日 0016 15:20
 */
@Slf4j
public class MultitenantCacheManager extends RedisCacheManager {

    private static final String SPLIT_FLAG = "#";

    private static final int CACHE_LENGTH = 2;

    public MultitenantCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration,
        Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    /**
     * 展@Cacheable，支持配置失效时间
     *
     * @param name
     * @param cacheConfig
     * @return
     */
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        if (StringUtils.isBlank(name) && !name.contains(SPLIT_FLAG)) {
            return super.createRedisCache(name, cacheConfig);
        }
        String[] cacheArray = name.split(SPLIT_FLAG);
        if (cacheArray.length < CACHE_LENGTH) {
            return super.createRedisCache(name, cacheConfig);
        }
        String cacheName = cacheArray[0];
        if (cacheConfig != null) {
            // 从系统属性里读取超时时间
            long cacheAge = Long.getLong(cacheArray[1], -1);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(cacheAge));
        }
        return super.createRedisCache(cacheName, cacheConfig);
    }

    /**
     * 扩展cache name，加上tenantCode作为前缀
     *
     * @param name
     * @return
     */
    @Override
    public Cache getCache(String name) {
        return super.getCache(SysUtil.getTenantCode() + ":" + name);
    }
}
