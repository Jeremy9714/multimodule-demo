package com.example.demo.constant.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 8:38
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResult<T> {

    private Integer code;

    private String message;

    private T data;

    public static <T> AjaxResult<T> success(T data) {
        return new AjaxResult<>(200, "success", data);
    }

    public static <T> AjaxResult<T> fail(T data) {
        return new AjaxResult<>(500, "fail", data);
    }
}
