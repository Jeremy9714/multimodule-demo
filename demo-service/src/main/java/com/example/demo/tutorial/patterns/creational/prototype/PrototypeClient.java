package com.example.demo.tutorial.patterns.creational.prototype;

import com.example.demo.tutorial.patterns.creational.prototype.entity.Circle;
import com.example.demo.tutorial.patterns.creational.prototype.entity.Rectangle;
import com.example.demo.tutorial.patterns.creational.prototype.entity.Shape;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 10:21
 * @Version: 1.0
 */
public class PrototypeClient {
    public static void main(String[] args) {
        Shape rectangle = new Rectangle();
        Shape circle = new Circle();

        Shape clonedRectangle = rectangle.clone();
        Shape clonedCircle = circle.clone();

        System.out.println(clonedRectangle.getType());
        System.out.println(clonedCircle.getType());
    }
}
