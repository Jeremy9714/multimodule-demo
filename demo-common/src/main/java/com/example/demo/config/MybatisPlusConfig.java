package com.example.demo.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.example.demo.interceptor.ExtendPaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/17 15:38
 * @Version: 1.0
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(Properties dspCustomProperties) {
        ExtendPaginationInterceptor paginationInterceptor = new ExtendPaginationInterceptor();
        Object obj = dspCustomProperties.get("pageinterceptor-dialect");
        if (obj != null) {
            String pageInterceptorDialectType = null;
            if (obj instanceof String) {
                pageInterceptorDialectType = (String) obj;
            } else {
                pageInterceptorDialectType = obj.toString();
            }

            if (!pageInterceptorDialectType.isEmpty()) {
                paginationInterceptor.setDialectType(pageInterceptorDialectType);
            }
        }

        return paginationInterceptor;
    }

}
