package com.example.demo.tutorial.patterns.creational.factorymethod.service;

import com.example.demo.tutorial.patterns.creational.factorymethod.entity.FactoryMethodWorkshop;
import com.example.demo.tutorial.patterns.creational.factorymethod.entity.factoryMethodWorkshopB;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:01
 * @Version: 1.0
 */
public class FactoryMethodB implements FactoryMethod {
    @Override
    public FactoryMethodWorkshop product() {
        return new factoryMethodWorkshopB();
    }
}
