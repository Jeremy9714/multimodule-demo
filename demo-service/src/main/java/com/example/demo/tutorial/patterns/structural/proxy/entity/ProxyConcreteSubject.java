package com.example.demo.tutorial.patterns.structural.proxy.entity;

/**
 * @Description: 静态代理
 * @Author: zhangchy05 on 2024/10/8 10:34
 * @Version: 1.0
 */
public class ProxyConcreteSubject implements ProxySubject {
    private ConcreteSubject concreteSubject;

    @Override
    public void request() {
        if (concreteSubject == null) {
            concreteSubject = new ConcreteSubject();
        }
        preRequest();
        concreteSubject.request();
        afterRequest();
    }

    private void preRequest() {
        System.out.println("代理类: 前置请求");
    }

    private void afterRequest() {
        System.out.println("代理类: 后置请求");
    }
}
