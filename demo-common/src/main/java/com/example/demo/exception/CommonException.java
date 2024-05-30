package com.example.demo.exception;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/29 16:52
 * @Version: 1.0
 */
public class CommonException extends BaseException {
    private static final long serialVersionUID = -3463463523L;

    public CommonException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
