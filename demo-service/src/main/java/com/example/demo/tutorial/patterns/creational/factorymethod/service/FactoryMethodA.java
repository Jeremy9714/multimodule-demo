package com.example.demo.tutorial.patterns.creational.factorymethod.service;

import com.example.demo.tutorial.patterns.creational.factorymethod.entity.FactoryMethodWorkshop;
import com.example.demo.tutorial.patterns.creational.factorymethod.entity.FactoryMethodWorkshopA;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:00
 * @Version: 1.0
 */
// 工厂A
public class FactoryMethodA implements FactoryMethod {
    @Override
    public FactoryMethodWorkshop product() {
        return new FactoryMethodWorkshopA();
    }
}
