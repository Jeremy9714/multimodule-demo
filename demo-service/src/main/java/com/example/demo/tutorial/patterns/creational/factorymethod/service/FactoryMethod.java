package com.example.demo.tutorial.patterns.creational.factorymethod.service;

import com.example.demo.tutorial.patterns.creational.factorymethod.entity.FactoryMethodWorkshop;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/8 9:59
 * @Version: 1.0
 */
// 工厂接口
public interface FactoryMethod {
    FactoryMethodWorkshop product();
}
