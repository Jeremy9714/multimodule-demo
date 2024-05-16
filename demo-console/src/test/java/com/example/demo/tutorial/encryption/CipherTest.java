package com.example.demo.tutorial.encryption;

import com.example.demo.console.Application;
import com.example.demo.util.AESUtils;
import com.example.demo.util.DESUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 16:59
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CipherTest {

    @Test
    public void test() {
        String content = "123456a?";
        String cipherText = AESUtils.encode(content);
        System.out.println("密文: " + cipherText);

        String pwd = AESUtils.decode(cipherText);
        System.out.println("密码: " + pwd);
    }

    @Test
    public void decodeAesTest() {
        String cipherText = "A7DF45A4998007CC92BF38D1D6225795";

        String pwd = AESUtils.decode(cipherText);
        System.out.println("密码: " + pwd);
    }

    @Test
    public void desTest() {
        try {
            String cipherText = DESUtils.encrypt("123456a?");
            System.out.println("密文: " + cipherText);

            String content = DESUtils.decrypt(cipherText);
            System.out.println("密码: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
