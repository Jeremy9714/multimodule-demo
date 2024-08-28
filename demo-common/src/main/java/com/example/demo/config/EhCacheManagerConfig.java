package com.example.demo.config;

//import net.sf.ehcache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/28 14:27
 * @Version: 1.0
 */
//@Configuration
public class EhCacheManagerConfig {

//    @Bean("cacheManager")
//    public EhCacheCacheManager cacheManager() {
//        CacheManager instance = CacheManager.getInstance();
//        if (instance == null) {
//            instance = CacheManager.create();
//        }
//        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(instance);
//        return ehCacheCacheManager;
//    }
}
