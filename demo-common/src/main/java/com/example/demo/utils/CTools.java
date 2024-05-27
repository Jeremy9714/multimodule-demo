package com.example.demo.utils;

import java.util.UUID;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 11:25
 * @Version: 1.0
 */
public class CTools {
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
