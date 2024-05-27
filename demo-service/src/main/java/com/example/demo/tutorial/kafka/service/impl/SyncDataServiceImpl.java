package com.example.demo.tutorial.kafka.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.kafka.TestData;
import com.example.demo.enumerate.OperationTypeProperties;
import com.example.demo.tutorial.kafka.provider.KafkaServiceProvider;
import com.example.demo.tutorial.kafka.service.SyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 14:10
 * @Version: 1.0
 */
@Service
@Slf4j
public class SyncDataServiceImpl implements SyncDataService {

    @Autowired
    private KafkaServiceProvider kafkaServiceProvider;

    @Override
    public JSONObject syncTestData(@Valid TestData testData, @NotBlank(message = "操作类型不能为空！") OperationTypeProperties operationType) {
        JSONObject result = new JSONObject();
        result.put("code", 400);
        result.put("msg", "数据推送失败");
        result.put("data", null);
        try {
            log.info("接收数据: {}", testData.toString());
            JSONObject jsonData = new JSONObject();
            jsonData.put("code", testData.getCode());
            jsonData.put("name", testData.getName());
            jsonData.put("age", String.valueOf(testData.getAge()));

            // 封装kafka数据
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("data", jsonData);
            jsonObj.put("operation", operationType.getOperationType());
            kafkaServiceProvider.sendTestObjToKafka(jsonObj);

            result.put("code", 200);
            result.put("msg", "数据推送成功");
            result.put("data", jsonObj);
        } catch (Exception e) {
            log.error("数据推送失败", e);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
