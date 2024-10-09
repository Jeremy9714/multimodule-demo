package com.example.demo.tutorial.patterns.creational.abstractfactory.service;

import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ConcreteProductA2;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ConcreteProductB2;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductA;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductB;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:11
 * @Version: 1.0
 */
public class ConcreteFactoryB implements AbstractFactory {
    @Override
    public ProductA createProductA() {
        return new ConcreteProductA2();
    }

    @Override
    public ProductB createProductB() {
        return new ConcreteProductB2();
    }
}
