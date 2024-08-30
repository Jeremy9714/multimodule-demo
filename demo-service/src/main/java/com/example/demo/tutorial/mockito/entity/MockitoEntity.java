package com.example.demo.tutorial.mockito.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:37
 * @Version: 1.0
 */
@Data
@TableName("mockito_tbl")
public class MockitoEntity implements Serializable {
    private final static long serialVersionUID = -353L;

    @TableId
    private String id;

    private String name;
}
