package com.example.demo.tutorial.patterns.creational.builder.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:25
 * @Version: 1.0
 */
@Data
public class BuilderProduct {
    private String productA;
    private String productB;

    public void show() {
        System.out.println("productA: " + productA);
        System.out.println("productB: " + productB);
    }
}
