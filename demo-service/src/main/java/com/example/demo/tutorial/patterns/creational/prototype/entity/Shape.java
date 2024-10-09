package com.example.demo.tutorial.patterns.creational.prototype.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:17
 * @Version: 1.0
 */
@Data
public class Shape implements Cloneable {
    private String type;

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
