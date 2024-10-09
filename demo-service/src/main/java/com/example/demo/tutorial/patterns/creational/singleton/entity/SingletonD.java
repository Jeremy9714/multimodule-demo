package com.example.demo.tutorial.patterns.creational.singleton.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:53
 * @Version: 1.0
 */
// 内部类型
public class SingletonD {

    private SingletonD() {
    }

    private static class SingletonHandler {
        private static final SingletonD instance = new SingletonD();
    }

    public static SingletonD getInstance() {
        return SingletonHandler.instance;
    }
}
