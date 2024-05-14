package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:32
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties
public class CustomPropertiesConfig {
    public CustomPropertiesConfig() {
    }

    @ConfigurationProperties(
            prefix = "custom"
    )
    @Bean({"dspCustomProperties"})
    public Properties dspCustomProperties() {
        Properties customProperties = new Properties();
        return customProperties;
    }
}
