package com.example.demo.tutorial.patterns.creational.abstractfactory.service;

import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ConcreteProductA1;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ConcreteProductB1;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductA;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductB;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:08
 * @Version: 1.0
 */
public class ConcreteFactoryA implements AbstractFactory {
    @Override
    public ProductA createProductA() {
        return new ConcreteProductA1();
    }

    @Override
    public ProductB createProductB() {
        return new ConcreteProductB1();
    }
}
