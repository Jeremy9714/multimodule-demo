package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:35
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig {
    public InterceptorConfig() {
    }

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }
}
