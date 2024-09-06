package com.example.demo.tutorial.login.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 9:47
 * @Version: 1.0
 */
@Data
@TableName("user_tbl")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = -234L;

    @TableId
    private String id;

    private String account;

    private String password;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_person")
    private String createPerson;
}
