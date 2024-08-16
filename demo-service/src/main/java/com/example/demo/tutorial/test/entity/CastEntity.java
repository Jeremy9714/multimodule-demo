package com.example.demo.tutorial.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/16 8:38
 * @Version: 1.0
 */
@Data
@TableName("cast_tbl")
public class CastEntity implements Serializable {

    private static final long serialVersionUID = -3453L;

    @TableId
    private String id;

}
