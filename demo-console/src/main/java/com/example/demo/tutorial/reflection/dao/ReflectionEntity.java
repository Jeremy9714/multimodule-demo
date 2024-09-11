package com.example.demo.tutorial.reflection.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 15:12
 * @Version: 1.0
 */
public class ReflectionEntity<T> {
    
    public ReflectionEntity(){}

    public Class<T> getType() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArgument = parameterizedType.getActualTypeArguments();
        return (Class<T>) actualTypeArgument[0];
    }
}
