package com.example.demo.tutorial.rpc;

import com.example.demo.api.basic.BasicRpcService;
import com.example.demo.console.Application;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/12 15:19
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
public class DubboTest {

    @DubboReference(group = "demo", version = "1.0", retries = 3)
    private BasicRpcService basicRpcService;

    @Test
    public void invokeTest() {
        basicRpcService.printMsg();
    }
}
