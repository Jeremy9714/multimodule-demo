package com.example.demo.tutorial.es.entity;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 10:53
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
public class Student {
    private String name;
    private Integer age;
    private String birthday;
}
