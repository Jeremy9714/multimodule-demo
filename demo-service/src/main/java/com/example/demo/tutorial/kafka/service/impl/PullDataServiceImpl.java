package com.example.demo.tutorial.kafka.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.tutorial.kafka.provider.KafkaServiceProvider;
import com.example.demo.tutorial.kafka.service.PullDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description:p
 * @Author: zhangchy05 on 2024/5/29 10:40
 * @Version: 1.0
 */
@Service
@Slf4j
public class PullDataServiceImpl implements PullDataService {

    @Autowired
    private KafkaServiceProvider kafkaServiceProvider;

    @Override
    public JSONObject pullTestData(@NotNull List<String> topics) {
        JSONObject result = new JSONObject();
        result.put("code", 400);
        result.put("msg", "数据消费失败");
        result.put("data", null);
        try {
            JSONArray jsonArr = kafkaServiceProvider.pullTestObjFromKafka(topics);
            
            result.put("code", 200);
            result.put("msg", "数据消费成功");
            result.put("data", jsonArr);
        } catch (Exception e) {
            log.error("数据消费失败", e);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
