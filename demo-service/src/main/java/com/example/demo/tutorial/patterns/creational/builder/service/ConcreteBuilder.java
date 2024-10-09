package com.example.demo.tutorial.patterns.creational.builder.service;

import com.example.demo.tutorial.patterns.creational.builder.entity.BuilderProduct;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:28
 * @Version: 1.0
 */
public class ConcreteBuilder implements Builder {
    private BuilderProduct product = new BuilderProduct();

    @Override
    public void buildProductA() {
        product.setProductA("产品A");
    }

    @Override
    public void buildProductB() {
        product.setProductB("产品B");
    }

    @Override
    public BuilderProduct getProduct() {
        return product;
    }
}
