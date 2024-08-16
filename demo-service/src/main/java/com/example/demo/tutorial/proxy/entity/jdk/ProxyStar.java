package com.example.demo.tutorial.proxy.entity.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/2 9:35
 * @Version: 1.0
 */
public class ProxyStar {

    private Object target;

    public ProxyStar(Object target) {
        this.target = target;
    }

    public Object createProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (proxy, method, args) -> {
            System.out.println("======proxyStart======");
            Object invoke = method.invoke(target, args);
            return invoke;
        });
    }
}
