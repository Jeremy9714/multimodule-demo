package com.example.demo.enumerate;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 14:04
 * @Version: 1.0
 */
public enum OperationTypeProperties {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete");

    public final String operationType;

    OperationTypeProperties(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationType() {
        return operationType;
    }
}
