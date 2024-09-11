package com.example.demo.tutorial.rpc.service;

import com.example.demo.tutorial.rpc.entity.StudentRpc;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 10:25
 * @Version: 1.0
 */
public interface StudentRpcService {

    StudentRpc getElementById(String id) throws Exception;
}
