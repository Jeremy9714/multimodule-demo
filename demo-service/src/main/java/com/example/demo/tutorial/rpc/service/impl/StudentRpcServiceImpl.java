package com.example.demo.tutorial.rpc.service.impl;

import com.example.demo.tutorial.rpc.entity.StudentRpc;
import com.example.demo.tutorial.rpc.service.StudentRpcService;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/11 10:27
 * @Version: 1.0
 */
public class StudentRpcServiceImpl implements StudentRpcService {
    @Override
    public StudentRpc getElementById(String id) throws Exception {
        return new StudentRpc(id, "Jeremy");
    }
}
