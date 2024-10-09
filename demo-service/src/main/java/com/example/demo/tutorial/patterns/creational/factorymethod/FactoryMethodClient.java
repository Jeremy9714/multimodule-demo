package com.example.demo.tutorial.patterns.creational.factorymethod;

import com.example.demo.tutorial.patterns.creational.factorymethod.entity.FactoryMethodWorkshop;
import com.example.demo.tutorial.patterns.creational.factorymethod.service.FactoryMethodA;
import com.example.demo.tutorial.patterns.creational.factorymethod.service.FactoryMethodB;

/**
 * @Description: 工厂方法测试
 * @Author: zhangchy05 on 2024/10/8 10:02
 * @Version: 1.0
 */
public class FactoryMethodClient {
    public static void main(String[] args) {
        FactoryMethodA factoryMethodA = new FactoryMethodA();
        FactoryMethodWorkshop productA = factoryMethodA.product();
        productA.produce();

        FactoryMethodB factoryMethodB = new FactoryMethodB();
        FactoryMethodWorkshop productB = factoryMethodB.product();
        productB.produce();
    }
}
