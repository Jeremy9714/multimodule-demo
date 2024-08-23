package com.example.demo.tutorial.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 8:52
 * @Version: 1.0
 */
@Data
@TableName("teacher_tbl")
public class Teacher implements Serializable {
    public static final long serialVersionUID = -234L;

    @TableId
    private String id;
    private String name;
}
