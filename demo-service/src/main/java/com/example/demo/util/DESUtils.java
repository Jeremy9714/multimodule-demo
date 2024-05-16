package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 16:41
 * @Version: 1.0
 */
@Slf4j
public class DESUtils {
    private static final String ALGORITHM = "DES";
    private static final String DEFAULT_KEY = "@#$%^$%^%^&*&hwx";
    
    public DESUtils(){}

    public static String encrypt(String originalString) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), "@#$%^$%^%^&*&hwx".getBytes());
        return parseHexStringFromBytes(bEn);
    }

    public static String encrypt(String originalString, String key) throws Exception {
        byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
        return parseHexStringFromBytes(bEn);
    }

    private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey keySpec = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, keySpec, sr);
        return cipher.doFinal(originalByte);
    }

    public static String decrypt(String encryptedString) throws Exception {
        byte[] bEn = parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, "@#$%^$%^%^&*&hwx".getBytes());
        return new String(orginal);
    }

    public static String decrypt(String encryptedString, String key) throws Exception {
        byte[] bEn = parseBytesByHexString(encryptedString);
        byte[] orginal = decrypt(bEn, key.getBytes());
        return new String(orginal);
    }

    private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey keySpec = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, keySpec, sr);
        return cipher.doFinal(encryptedByte);
    }

    public static String parseHexStringFromBytes(byte[] text) {
        StringBuffer buff = new StringBuffer(0);

        for(int i = 0; i < text.length; ++i) {
            byte _byte = text[i];
            byte _bytel = (byte)(_byte & 15);
            byte _byteh = (byte)(_byte & 240);
            getHexString(buff, (byte)(_byteh >> 4 & 15));
            getHexString(buff, _bytel);
        }

        return buff.toString();
    }

    private static void getHexString(StringBuffer buffer, byte value) {
        if (value - 9 > 0) {
            int index = value - 9;
            switch(index) {
                case 1:
                    buffer.append("A");
                    break;
                case 2:
                    buffer.append("B");
                    break;
                case 3:
                    buffer.append("C");
                    break;
                case 4:
                    buffer.append("D");
                    break;
                case 5:
                    buffer.append("E");
                    break;
                case 6:
                    buffer.append("F");
            }
        } else {
            buffer.append(String.valueOf(value));
        }

    }

    public static byte[] parseBytesByHexString(String hexString) {
        if (hexString != null && hexString.length() != 0 && !hexString.equals("")) {
            if (hexString.length() % 2 != 0) {
                throw new IllegalArgumentException("hexString length must be an even number!");
            } else {
                StringBuffer sb = new StringBuffer(hexString);
                StringBuffer sb1 = new StringBuffer(2);
                int n = hexString.length() / 2;
                byte[] bytes = new byte[n];

                for(int i = 0; i < n; ++i) {
                    if (sb1.length() > 1) {
                        sb1.deleteCharAt(0);
                        sb1.deleteCharAt(0);
                    }

                    sb1.append(sb.charAt(0));
                    sb1.append(sb.charAt(1));
                    sb.deleteCharAt(0);
                    sb.deleteCharAt(0);
                    bytes[i] = (byte)Integer.parseInt(sb1.toString(), 16);
                }

                return bytes;
            }
        } else {
            return new byte[0];
        }
    }
}
