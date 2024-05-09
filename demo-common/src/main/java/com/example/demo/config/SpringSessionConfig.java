package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:37
 * @Version: 1.0
 */
@Configuration
@EnableRedisHttpSession
public class SpringSessionConfig {
    @Value("${server.servlet.session.cookie.name:SESSION}")
    private String cookieName;
    @Value("${server.servlet.session.cookie:/}")
    private String cookiePath;
    @Value("${server.servlet.session.cookie.http-only:false}")
    private Boolean cookieHttpOnly;
    @Value("${server.servlet.session.cookie.max-age:-1}")
    private Integer cookieMaxAge;

    public SpringSessionConfig() {
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(cookieName);
        serializer.setUseHttpOnlyCookie(cookieHttpOnly);
        serializer.setCookiePath(cookiePath);
        serializer.setSameSite((String) null);
        serializer.setUseBase64Encoding(false);
        serializer.setCookieMaxAge(-1);
        return serializer;
    }

    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
