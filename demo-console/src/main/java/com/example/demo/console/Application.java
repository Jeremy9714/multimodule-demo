package com.example.demo.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 10:11
 * @Version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
@EnableAsync
@EnableScheduling
@ServletComponentScan(value = {"com.example"})
public class Application {
    public static void main(String[] args) {
        try {
            System.setProperty("fastjson.serializer_buffer_threshold","64");
            SpringApplication.run(Application.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
