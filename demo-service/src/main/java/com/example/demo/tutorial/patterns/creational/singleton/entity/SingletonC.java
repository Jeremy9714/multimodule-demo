package com.example.demo.tutorial.patterns.creational.singleton.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:51
 * @Version: 1.0
 */
// 线程安全型
public class SingletonC {

    private static SingletonC instance;

    private SingletonC() {
    }

    public static SingletonC getInstance() {
        if (instance == null) {
            synchronized (SingletonC.class) {
                if (instance == null) {
                    instance = new SingletonC();
                }
            }
        }
        return instance;
    }
}
