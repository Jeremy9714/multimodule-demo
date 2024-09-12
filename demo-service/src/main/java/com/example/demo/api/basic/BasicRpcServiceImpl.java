package com.example.demo.api.basic;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/12 14:28
 * @Version: 1.0
 */
@DubboService(group = "basic", version = "1.0")
public class BasicRpcServiceImpl implements BasicRpcService {
    @Override
    public void printMsg() {
        System.out.println("======dubbo调用测试======");
    }
}
