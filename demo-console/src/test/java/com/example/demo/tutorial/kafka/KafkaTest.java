package com.example.demo.tutorial.kafka;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.constant.kafka.TestData;
import com.example.demo.enumerate.OperationTypeProperties;
import com.example.demo.tutorial.kafka.service.SyncDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void syncDataToKafkaTest() {
        TestData testData = new TestData();
        testData.setCode("101");
        testData.setName("no.101");
        testData.setAge(18);
        JSONObject jsonObject = syncDataService.syncTestData(testData, OperationTypeProperties.INSERT);
        System.out.println(jsonObject.toString());
    }
}
