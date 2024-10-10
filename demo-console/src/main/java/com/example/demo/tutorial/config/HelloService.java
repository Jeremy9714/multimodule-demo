package com.example.demo.tutorial.config;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 9:17
 * @Version: 1.0
 */
@Data
public class HelloService {
    private String msg;

    public void sayHello() {
        System.out.println("hello message: " + msg);
    }
}
