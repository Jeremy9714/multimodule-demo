package com.example.demo.tutorial.kafka.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/29 10:15
 * @Version: 1.0
 */
public interface PullDataService {

    JSONObject pullTestData(List<String> topics);
}
