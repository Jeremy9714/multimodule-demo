package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:15
 * @Version: 1.0
 */
@Configuration
@ConfigurationProperties(
        value = "spring.freemarker",
        ignoreInvalidFields = true,
        ignoreUnknownFields = true
)
public class FreeMarkerConfig extends FreeMarkerAutoConfiguration {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private freemarker.template.Configuration configuration;
    private Map<String, String> autoImportMap = new HashMap<>();

    public FreeMarkerConfig(ApplicationContext applicationContext, FreeMarkerProperties properties) {
        super(applicationContext, properties);
    }

    @PostConstruct
    public void setSharedVariable() {
        this.configuration.setDateFormat(DATE_FORMAT_PATTERN);
        this.configuration.setDateTimeFormat(DATETIME_FORMAT_PATTERN);
        this.autoImportMap.put("webfinal", "common/ftl/webfinal.ftl");
        this.configuration.setAutoImports(autoImportMap);
    }
}
