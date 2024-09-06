package com.example.demo.config;

import com.example.demo.filter.test.RegistrationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 10:58
 * @Version: 1.0
 */
@Configuration
public class FilterConfig {

    @Bean(name = "registrationFilter")
    public FilterRegistrationBean<RegistrationFilter> createRegistrationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new RegistrationFilter());
//        registrationBean.addUrlPatterns(new String[]{"/static/*"});
        registrationBean.addUrlPatterns(new String[]{"/*"});
        registrationBean.setName("registrationFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
