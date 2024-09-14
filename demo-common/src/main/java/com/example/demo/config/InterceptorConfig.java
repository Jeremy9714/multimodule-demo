package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.interceptor.test.MyLoginInterceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:35
 * @Version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${global.login.exclude.path:}")
    private String loginExclude;

//    @Bean
//    public LoginInterceptor getLoginInterceptor() {
//        return new LoginInterceptor();
//    }

    @Bean
    public MyLoginInterceptor myLoginInterceptor() {
        return new MyLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(myLoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(new String[]{"/static/**"});
        if (StringUtils.isNotBlank(this.loginExclude)) {
            String[] excludePaths = this.loginExclude.split(",");
            for (String path : excludePaths) {
                if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(path.trim())) {
                    registration.excludePathPatterns(new String[]{path.trim()});
                }
            }
        }
        registry.addInterceptor(myLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index", "/login");
    }
}
