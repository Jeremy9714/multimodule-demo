package com.example.demo.tutorial.patterns.creational.builder.service;

import com.example.demo.tutorial.patterns.creational.builder.entity.BuilderProduct;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:29
 * @Version: 1.0
 */
public class Director {
    public BuilderProduct struct(Builder builder) {
        builder.buildProductA();
        builder.buildProductB();
        return builder.getProduct();
    }
}
