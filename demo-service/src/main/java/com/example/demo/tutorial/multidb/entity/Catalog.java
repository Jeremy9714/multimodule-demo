package com.example.demo.tutorial.multidb.dao;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:28
 * @Version: 1.0
 */
@Data
@TableName("catalog")
public class Catalog implements Serializable {
    public static final long serialVersionUID = 3465345L;
    @TableId
    private String id;
    @TableField("cata_title")
    private String cataTitle;
    @TableField("cata_code")
    private String cataCode;
    @TableField("group_id")
    private String groupId;
    @TableField("org_code")
    private String orgCode;
    @TableField("org_name")
    private String orgName;
    @TableField("region_code")
    private String regionCode;
    @TableField("region_name")
    private String regionName;
    @TableField("open_type")
    private Integer openType;
    private Date createTime;
    private Date updateTime;
}
