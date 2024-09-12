package com.example.demo.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/12 9:33
 * @Version: 1.0
 */
@EnableAsync
@ServletComponentScan(basePackages = "com.example.demo.job")
@SpringBootApplication(scanBasePackages = "com.example.demo.job")
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(XxlJobExecutorApplication.class);
//        application.setAllowCircularReferences(true);
        application.run(args);
    }
}
