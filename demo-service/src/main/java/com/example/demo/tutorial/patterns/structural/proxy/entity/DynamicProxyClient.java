package com.example.demo.tutorial.patterns.structural.proxy.entity;

import java.lang.reflect.Proxy;

/**
 * @Description: 动态代理
 * @Author: zhangchy05 on 2024/10/8 10:37
 * @Version: 1.0
 */
public class DynamicProxyClient {

    public static void main(String[] args) {
        ConcreteSubject concreteSubject = new ConcreteSubject();
        ProxySubject proxySubject =
                (ConcreteSubject) Proxy.newProxyInstance(concreteSubject.getClass().getClassLoader(), concreteSubject.getClass().getInterfaces(),
                        (proxy, method, arg) -> method.invoke(proxy, arg));
        proxySubject.request();
    }
}
