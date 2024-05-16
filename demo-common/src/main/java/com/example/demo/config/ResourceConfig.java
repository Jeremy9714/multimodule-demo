package com.example.demo.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:08
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(
        value = "spring.messages",
        ignoreInvalidFields = true,
        ignoreUnknownFields = true
)
public class ResourceConfig {
    private String basename;
    private int cacheSeconds;
    private String encoding;

    public ResourceConfig() {
    }

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public int getCacheSeconds() {
        return cacheSeconds;
    }

    public void setCacheSeconds(int cacheSeconds) {
        this.cacheSeconds = cacheSeconds;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        if (StringUtils.isNotBlank(this.getBasename())) {
            if (this.getBasename().indexOf(",") == 1) {
                messageSource.setBasenames(this.getBasename());
            } else {
                messageSource.setBasenames(this.getBasename().split(","));
            }
        }

        messageSource.setCacheSeconds(5);
        messageSource.setCacheSeconds(this.getCacheSeconds());
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding(this.getEncoding());
        return messageSource;
    }
}
