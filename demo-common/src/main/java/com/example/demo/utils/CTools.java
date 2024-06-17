package com.example.demo.utils;

import java.security.MessageDigest;
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

    public static String getMD5(String source) {
        return source == null ? "" : getMD5(source.getBytes());
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            char[] str = new char[32];
            int k = 0;

            for (int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }
}
