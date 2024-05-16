package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:44
 * @Version: 1.0
 */
public final class License {
    private static final Logger loger = LoggerFactory.getLogger(License.class);
    private static DateFormat tf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static final byte[] rawKeyData = "com.inspur.dsp.2018".getBytes();
    public static final String SYSTEM_NO = "0";
    public static final String SYSTEM_YES = "1";
    public static final String OBJ_RTIME = "REGISTER";
    public static final String SYSTEM_TRY_TIME = "5";
    public static final String OBJ_UID = "JAVA-UIID";
    public static final String OBJ_NAME = "NAME";
    public static final String OBJ_DATE = "DATE";
    public static final String OBJ_TYPE = "TYPE";
    public static final String OBJ_YEAR = "YEAR";
    public static final String OBJ_MONTH = "MONTH";
    public static final String OBJ_DAY = "DAY";
    public static final String OBJ_VERSION = "VERSION";
    public static final String OBJ_PREFIX = "PREFIX";
    public static final String OBJ_SUFFIX = "SUFFIX";
    public static final String OBJ_KEY = "KEY";
    public static final String OBJ_RDATE = "RDATE";
    public static final String OBJ_TIME = "TIME";
    public static final String OBJ_PASSWD = "PASSWORD";
    public static final String OBJ_PWD = "JAMES_PASSWD_KEY";
    public static final char[] COMPANY_NAME = new char[]{'C', 'l', 'o', 'u', 'd'};
    public static final char[] LICENSE_NAME = new char[]{'l', 'i', 'c', 'e', 'n', 's', 'e'};
    public static final String pwd = "dsp-inspur";

    public License() {
    }

    public static String getLicenseName() {
        return String.valueOf(LICENSE_NAME);
    }

    public static String getLicense() {
        return LICENSE_NAME + ".dat";
    }

    public static String getCompany() {
        return "[" + getTime() + "-" + COMPANY_NAME + "]:";
    }

    public static String getTime() {
        return tf.format(new Date());
    }

    public static String getSystemId(String key) {
        return generateID(key);
    }

    private static String generateID(String key) {
        String password = "dsp-inspur-" + key;
        String encryptedPassword = getBASE64(password);
        return MD5(encryptedPassword);
    }

    private static String getBASE64(String s) {
        if (s == null) {
            return null;
        } else {
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(s.getBytes());
        }
    }

    private static String MD5(String inStr) {
        MessageDigest md5 = null;
        StringBuffer hexValue = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("ISO-8859-1");
            byte[] md5Bytes = md5.digest(byteArray);

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }
        } catch (RuntimeException var7) {
            throw var7;
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }

        return hexValue.toString();
    }

    public static String getGovermentServicePlatformID() {
        return getSystemId(KeyUtils.getJniMacAddress());
    }

    private static String decrypt(byte[] encryptedData) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, key, sr);
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    private static JSONObject getKey(String key) {
        loger.info(key);

        try {
            byte[] bytes = key.getBytes();
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedByte = decoder.decode(bytes);
            String code = decrypt(encryptedByte);
            JSONObject obj = JSONObject.parseObject(code);
            return obj;
        } catch (RuntimeException var6) {
            loger.warn("please check the license.dat,license Invalid");
        } catch (Exception var7) {
            loger.warn("please check the license.dat,license Invalid");
        }

        return null;
    }

    public static JSONObject parseKey(String key) {
        return getKey(key);
    }
}
