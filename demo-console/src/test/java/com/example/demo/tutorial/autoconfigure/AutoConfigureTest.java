package com.example.demo.tutorial.autoconfigure;

import com.example.demo.console.Application;
import com.example.demo.tutorial.config.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/10/10 9:29
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
public class AutoConfigureTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void test1() {
        helloService.sayHello();
    }
}
