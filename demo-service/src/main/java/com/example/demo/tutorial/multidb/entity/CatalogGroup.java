package com.example.demo.tutorial.multidb.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:34
 * @Version: 1.0
 */
@Data
@TableName("catalog_group")
public class CatalogGroup implements Serializable {
    public static final long serialVersionUID = -34534L;

    @TableId
    private String groupId;
    @TableField("group_name")
    private String groupName;
    @TableField("group_status")
    private String groupStatus;
    @TableField("creator_id")
    private String creatorId;
    @TableField("creator_name")
    private String creatorName;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    
}
