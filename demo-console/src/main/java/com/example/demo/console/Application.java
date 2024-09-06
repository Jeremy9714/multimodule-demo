package com.example.demo.console;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 10:11
 * @Version: 1.0
 * @SpringBootApplication等价于下面标签：
 *   1. @Configuration
 *   2. @EnableAutoConfiguration
 *   3. @EnableWebMvc
 *   4. @ComponentScan
 * @ServletComponentScan
 * 	 1. @WebListener
 *   2. @WebFilter
 *   3. @WebSevlet
 */
@SpringBootApplication(scanBasePackages = {"com.example"}, exclude = {DataSourceAutoConfiguration.class})
@EnableAsync
@EnableScheduling
@ServletComponentScan(value = {"com.example"})
@MapperScan(basePackages = {"com.example.demo.**.dao"})
@EnableCaching
public class Application {
    public static void main(String[] args) {
        try {
            System.setProperty("fastjson.serializer_buffer_threshold", "64");
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
