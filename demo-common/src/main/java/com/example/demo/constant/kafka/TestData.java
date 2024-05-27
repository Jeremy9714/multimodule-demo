package com.example.demo.constant.kafka;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 14:06
 * @Version: 1.0
 */
@Data
public class TestData {

    @NotBlank(message = "编码不能为空！")
    @Length(min = 10, max = 20)
    private String code;

    @NotBlank(message = "名称不能为空！")
    private String name;

    private Integer age;
}
