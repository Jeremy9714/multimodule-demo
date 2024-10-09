package com.example.demo.tutorial.patterns.creational.abstractfactory;

import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductA;
import com.example.demo.tutorial.patterns.creational.abstractfactory.entity.ProductB;
import com.example.demo.tutorial.patterns.creational.abstractfactory.service.AbstractFactory;
import com.example.demo.tutorial.patterns.creational.abstractfactory.service.ConcreteFactoryA;
import com.example.demo.tutorial.patterns.creational.abstractfactory.service.ConcreteFactoryB;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:12
 * @Version: 1.0
 */
public class AbstractFactoryClient {

    private ProductA productA;
    private ProductB productB;

    public AbstractFactoryClient(AbstractFactory factory) {
        productA = factory.createProductA();
        productB = factory.createProductB();
    }

    public void run() {
        productA.use();
        productB.use();
    }

    public static void main(String[] args) {
        AbstractFactory factoryA = new ConcreteFactoryA();
        AbstractFactoryClient clientA = new AbstractFactoryClient(factoryA);
        clientA.run();

        AbstractFactory factoryB = new ConcreteFactoryB();
        AbstractFactoryClient clientB = new AbstractFactoryClient(factoryB);
        clientB.run();
    }
}
