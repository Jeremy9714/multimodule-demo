package com.example.demo.util.multidatabase.exception;

import lombok.Data;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/14 14:19
 * @Version: 1.0
 */
@Data
public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = -2342L;

    private ExceptionEnum error; // 异常码和异常信息
    private String errorMethod; // 异常方法名
    private String errorDetail; // 异常详细信息

    public DatabaseException(ExceptionEnum error) {
        super();
        this.error = error;
    }

    public DatabaseException(ExceptionEnum error, String errorMethod, String errorDetail) {
        super();
        this.error = error;
        this.errorMethod = errorMethod;
        this.errorDetail = errorDetail;
    }
}
