package com.example.demo.tutorial.patterns.structural.proxy.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:33
 * @Version: 1.0
 */
public class ConcreteSubject implements ProxySubject {
    @Override
    public void request() {
        System.out.println("实体类的请求");
    }
}
