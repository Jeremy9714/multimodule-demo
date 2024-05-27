package com.example.demo.tutorial.kafka.provider;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.utils.CTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 10:08
 * @Version: 1.0
 */
@Service
@Slf4j
public class KafkaServiceProvider {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.test-topic:}")
    public String testTopic;

    private void sendInfoToKafka(String topic, JSONObject object) {
//        kafkaTemplate.send(topic, CTools.getUUID(), JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue))
//                .addCallback(success -> {
//                    String topicName = success.getRecordMetadata().topic();
//                    int partition = success.getRecordMetadata().partition();
//                    long offset = success.getRecordMetadata().offset();
//                    log.info("发送消息成功：{}-{}-{}", topicName, partition, offset);
//                }, failure -> {
//                    log.error("发送消息失败：{}", failure.getMessage());
//                });
        
        kafkaTemplate.send(topic, CTools.getUUID(),JSONObject.toJSONString(object,SerializerFeature.WriteMapNullValue));
    }

    @Async
    public void sendTestObjToKafka(JSONObject object) {
        try {
            sendInfoToKafka(testTopic, object);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(object.toJSONString());
            e.printStackTrace();
        }
    }
}
