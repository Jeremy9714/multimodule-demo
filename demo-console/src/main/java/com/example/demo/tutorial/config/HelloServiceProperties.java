package com.example.demo.tutorial.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 9:14
 * @Version: 1.0
 */
//@Component
@Data
@ConfigurationProperties(prefix = "hello")
public class HelloServiceProperties {
    private static final String MSG = "默认信息";
    
    private String msg;
}
