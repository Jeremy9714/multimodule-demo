package com.example.demo.tutorial.proxy.entity.cglib;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/2 9:39
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    private String name;

    public void printInfo() {
        System.out.println("this is " + name + "");
    }
}
