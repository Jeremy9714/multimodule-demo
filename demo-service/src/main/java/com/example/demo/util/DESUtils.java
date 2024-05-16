package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 16:41
 * @Version: 1.0
 */
@Slf4j
public class AESUtils {

    private static String aes_key_path = "aes.key";

    // 加载密钥文件
    private static SecretKey privateKey = null;

//    static {
//        try {
//            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(aes_key_path);
//            byte[] bytes = toByteArray(is);
//            privateKey = new SecretKeySpec(bytes, "aes");
//        } catch (FileNotFoundException e) {
//            log.error("未找到密钥文件: ", aes_key_path, e);
//        } catch (IOException e) {
//            log.error("读取密钥文件出错: ", e);
//        }
//    }
    
    static{
        ObjectInputStream ois = null;

        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(aes_key_path);
            ois = new ObjectInputStream(inputStream);
            privateKey = (SecretKey)ois.readObject();
        } catch (FileNotFoundException var14) {
            log.error("未找到密钥文件：" + aes_key_path, var14);
        } catch (IOException var15) {
            log.error("读取密钥文件出错＿" + aes_key_path, var15);
        } catch (ClassNotFoundException var16) {
            log.error((String) null, var16);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException var13) {
                    log.error("", var13);
                }
            }

        }
    }

    // 使用密钥文件中的密钥进行加密解密
    public static String encode(String content) {
        return encode(content, privateKey);
    }

    public static String decode(String cipherText) {
        return decode(cipherText, privateKey);
    }

    public static String encode(String content, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] b = cipher.doFinal(content.getBytes("utf-8"));
            return Base64.encodeBase64String(b);
        } catch (NoSuchPaddingException e) {
            log.error("", e);
        } catch (InvalidKeyException e) {
            log.error("", e);
        } catch (IllegalBlockSizeException e) {
            log.error("", e);
        } catch (BadPaddingException e) {
            log.error("", e);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("", e);
        }
        return null;
    }
    

    public static String decode(String cipherText, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "utf-8");
        } catch (NoSuchPaddingException e) {
            log.error("解密{}出错", cipherText, e);
        } catch (NoSuchAlgorithmException e) {
            log.error("解密{}出错", cipherText, e);
        } catch (InvalidKeyException e) {
            log.error("解密{}出错", cipherText, e);
        } catch (BadPaddingException e) {
            log.error("解密{}出错", cipherText, e);
        } catch (UnsupportedEncodingException e) {
            log.error("解密{}出错", cipherText, e);
        } catch (IllegalBlockSizeException e) {
            log.error("解密{}出错", cipherText, e);
        }
        return cipherText;
    }

    private static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = is.read(buffer)) != -1) {
            bos.write(buffer, 0, n);
        }
        return bos.toByteArray();
    }


}
