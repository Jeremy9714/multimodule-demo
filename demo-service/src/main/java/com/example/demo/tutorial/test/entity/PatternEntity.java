package com.example.demo.tutorial.test.entity;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/24 9:35
 * @Version: 1.0
 */
@Data
public class PatternEntity implements Serializable {
    private static final long serialVersionUID = 345345L;
    
    private String id;
    
    private String name;

    @Pattern(regexp = "^([1-6][1-9]|50)\\d{4}(18|19|20)\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",message = "用户身份证号 identity_num 不合法")
    @Pattern(regexp = "^([1-6][1-9]|50)\\d{4}\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}$",message = "用户身份证号 identity_num 不合法")
    private String identityNum;
}
