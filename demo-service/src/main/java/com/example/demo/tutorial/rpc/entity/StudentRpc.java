package com.example.demo.tutorial.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 10:25
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRpc implements Serializable {
    private static final long serialVersionUID = -234L;

    private String id;

    private String name;
}
