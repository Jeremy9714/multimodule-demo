package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 14:32
 * @Version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(
        value = "spring.datasource",
        ignoreInvalidFields = true,
        ignoreUnknownFields = true
)
public class DruidDataSourceConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int maxActive = 20;
    private int minIdle = 5;
    private int initialSize = 1;
    private int maxWait = 3000;
    private int timeBetweenEvictionRunsMillis = 2000;
    private int minEvictableIdleTimeMillis = 600000;
    private int maxEvictableIdleTimeMillis = 900000;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean testWhileIdle = true;
    private String validationQuery;
    private boolean poolPreparedStatements = false;
    private int maxOpenPreparedStatements = 20;
    private String filters = "stat";

    public DruidDataSourceConfig() {
    }
    
}
