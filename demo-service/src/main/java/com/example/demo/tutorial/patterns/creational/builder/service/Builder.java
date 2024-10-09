package com.example.demo.tutorial.patterns.creational.builder.service;

import com.example.demo.tutorial.patterns.creational.builder.entity.BuilderProduct;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:27
 * @Version: 1.0
 */
public interface Builder {
    void buildProductA();

    void buildProductB();

    BuilderProduct getProduct();
}
