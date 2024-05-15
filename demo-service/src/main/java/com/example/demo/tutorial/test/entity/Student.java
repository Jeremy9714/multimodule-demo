package com.example.demo.tutorial.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 11:04
 * @Version: 1.0
 */
@Data
@TableName("student")
public class Student implements Serializable {
    public static final long serialVersionUID = -34634L;
    @TableId
    private String id;
    private String name;
    private Integer age;
}
