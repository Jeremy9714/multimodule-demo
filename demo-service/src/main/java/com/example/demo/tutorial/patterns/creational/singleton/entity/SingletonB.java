package com.example.demo.tutorial.patterns.creational.singleton.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:50
 * @Version: 1.0
 */
// 懒汉式
public class SingletonB {

    private static SingletonB instance;

    private SingletonB() {
    }

    public static SingletonB getSingleton() {
        if (instance == null) {
            instance = new SingletonB();
        }
        return instance;
    }
}
