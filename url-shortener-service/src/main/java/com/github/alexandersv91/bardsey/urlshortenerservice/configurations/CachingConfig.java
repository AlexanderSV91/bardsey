package com.github.alexandersv91.bardsey.urlshortenerservice.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableCaching
public class CachingConfig {

    public final static String SHORT_URLS_CACHE = "shortUrls";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(SHORT_URLS_CACHE);
    }

    @CacheEvict(value = SHORT_URLS_CACHE, allEntries = true)
    @Scheduled(cron = "0 0 1 * * *")
    public void clearUrlsCache() {
        log.info("Clearing cache: {}", SHORT_URLS_CACHE);
    }
}
