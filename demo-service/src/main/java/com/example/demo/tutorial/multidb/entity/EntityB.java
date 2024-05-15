package com.example.demo.tutorial.multidb.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 10:15
 * @Version: 1.0
 */
@Data
@TableName("tb_b")
public class EntityB implements Serializable {
    public static final long serialVersionUID = 34534L;
    private String id;
    private String name;
    private Integer age;
}
