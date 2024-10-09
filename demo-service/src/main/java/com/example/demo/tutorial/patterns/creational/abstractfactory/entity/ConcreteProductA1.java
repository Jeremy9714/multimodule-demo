package com.example.demo.tutorial.patterns.creational.abstractfactory.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:06
 * @Version: 1.0
 */
public class ConcreteProductA1 implements ProductA {
    @Override
    public void use() {
        System.out.println("使用产品A1");
    }
}
