package com.example.demo.tutorial.proxy.entity.jdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/2 9:34
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigStar implements IStar {

    private String name;

    @Override
    public void printInfo() {
        System.out.println("======this is " + name + "======");
    }
}
