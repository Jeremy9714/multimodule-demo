package com.example.demo.tutorial.kafka.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.kafka.TestData;
import com.example.demo.enumerate.OperationTypeProperties;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 14:00
 * @Version: 1.0
 */
public interface SyncDataService {

    JSONObject syncTestData(@Valid TestData testData, @NotBlank(message = "操作类型不能为空！") OperationTypeProperties operationType);
}
