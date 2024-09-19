package com.example.demo.util.multidatabase.exception;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/14 14:20
 * @Version: 1.0
 */
public enum ExceptionEnum {

    ADDRESS_CONNECTION_EXCEPTION(101, "地址链接异常"),
    CLOSE_CONNECTION_EXCEPTION(102, "关闭连接异常");

    private int errorCode;
    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    ExceptionEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
