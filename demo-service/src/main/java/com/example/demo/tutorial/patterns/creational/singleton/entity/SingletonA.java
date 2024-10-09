package com.example.demo.tutorial.patterns.creational.singleton.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:48
 * @Version: 1.0
 */
// 饿汉式
public class SingletonA {

    private static final SingletonA INSTANCE = new SingletonA();

    private SingletonA() {
    }

    public static SingletonA getSingletonA() {
        return INSTANCE;
    }
}
