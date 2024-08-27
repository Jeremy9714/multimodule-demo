package com.example.demo.tutorial.es.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 10:53
 * @Version: 1.0
 */
@ApiModel("学生实体类")
@Data
@Accessors(chain = true)
public class Student {
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("生日")
    private String birthday;
}
