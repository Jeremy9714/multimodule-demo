package com.example.demo.tutorial.patterns.creational.abstractfactory.service;

import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductA;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductB;

/**
 * @Description: 抽象工厂接口
 * @Author: zhangchy05 on 2024/10/8 10:07
 * @Version: 1.0
 */
public interface AbstractFactory {
    ProductA createProductA();

    ProductB createProductB();
}
