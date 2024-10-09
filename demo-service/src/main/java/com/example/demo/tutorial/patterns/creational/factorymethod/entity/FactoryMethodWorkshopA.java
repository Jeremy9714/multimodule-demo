package com.example.demo.tutorial.patterns.creational.factorymethod.entity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:58
 * @Version: 1.0
 */
public class FactoryMethodWorkshopA implements FactoryMethodWorkshop{
    @Override
    public void produce() {
        System.out.println("工厂车间A生产方法调用");
    }
}
