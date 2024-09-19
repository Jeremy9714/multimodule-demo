package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/19 14:16
 * @Version: 1.0
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        return restTemplate;
    }
}
