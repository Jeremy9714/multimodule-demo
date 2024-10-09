package com.example.demo.tutorial.patterns.creational.builder;

import com.example.demo.tutorial.patterns.creational.builder.entity.BuilderProduct;
import com.example.demo.tutorial.patterns.creational.builder.service.Builder;
import com.example.demo.tutorial.patterns.creational.builder.service.ConcreteBuilder;
import com.example.demo.tutorial.patterns.creational.builder.service.Director;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:30
 * @Version: 1.0
 */
public class BuilderClient {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director();
        BuilderProduct product = director.struct(builder);
        product.show();
    }
}
