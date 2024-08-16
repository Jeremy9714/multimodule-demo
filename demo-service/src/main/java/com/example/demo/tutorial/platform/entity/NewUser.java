package com.example.demo.tutorial.platform.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/20 9:29
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@TableName("new_user")
public class NewUser implements Serializable {

    private static final long serialVersionUID = -3532L;
    
    @TableId
    private String userId;
    
    private String name;
    
    private String mobile;
    
    private String gender;
    
    @TableField("is_delete")
    private String idDelete;
    
    @TableField("department_id")
    private String departmentId;

    @TableField("department_name")
    private String departmentName;
    
    private String updateTime;
    
    private String status;

}
