package com.example.demo.tutorial.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.constant.kafka.TestData;
import com.example.demo.enumerate.OperationTypeProperties;
import com.example.demo.tutorial.kafka.service.PullDataService;
import com.example.demo.tutorial.kafka.service.SyncDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 14:18
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class KafkaTest {

    @Autowired
    private SyncDataService syncDataService;

    @Autowired
    private PullDataService pullDataService;

    @Test
    public void syncDataToKafkaTest() {
        TestData testData = new TestData();
        testData.setCode("101");
        testData.setName("no.101");
        testData.setAge(18);
        JSONObject jsonObject = syncDataService.syncTestData(testData, OperationTypeProperties.INSERT);
        System.out.println(jsonObject.toString());
    }

    @Test
    public void pullDataFromKafkaTest() {
        List<String> topics = Collections.singletonList("test-topic");
        JSONObject jsonObject = pullDataService.pullTestData(topics);
        JSONArray arr = jsonObject.getJSONArray("data");
        arr.forEach(System.out::println);
    }
}
