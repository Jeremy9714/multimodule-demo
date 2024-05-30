package com.example.demo.tutorial.kafka.provider;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.utils.CTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Autowired(required = false)
    private KafkaConsumer<String, String> kafkaConsumer;

    @Value("${kafka.test-topic:}")
    public String testTopic;

    private void sendInfoToKafka(String topic, JSONObject object) {
        kafkaTemplate.send(topic, CTools.getUUID(), JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue))
                .addCallback(success -> {
                    String topicName = success.getRecordMetadata().topic();
                    int partition = success.getRecordMetadata().partition();
                    long offset = success.getRecordMetadata().offset();
                    log.info("发送消息成功：{}-{}-{}", topicName, partition, offset);
                }, failure -> {
                    log.error("发送消息失败：{}", failure.getMessage());
                });

//        kafkaTemplate.send(topic, CTools.getUUID(),JSONObject.toJSONString(object,SerializerFeature.WriteMapNullValue));
    }

    private JSONArray pullInfoFromKafka(List<String> topics) throws Exception {
        // 订阅topic
//        kafkaConsumer.subscribe(Collections.singletonList(testTopic));
        kafkaConsumer.subscribe(topics);
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
        JSONArray jsonArr = new JSONArray();
        for (ConsumerRecord record : records) {
            log.info("topic={}, partition={}, offset={}, key={}, value={}",
                    record.topic(), record.partition(), record.offset(), record.key(), record.value());
            String objStr = JSON.toJSONString(record.value());
            JSONObject jsonObject = JSONObject.parseObject(objStr);
            jsonArr.add(jsonObject);
        }
//        // 手动提交offset (同步阻塞，自动失败重试)
//        kafkaConsumer.commitSync();

        // 异步提交，无失败重试
        if (!records.isEmpty()) {
            kafkaConsumer.commitAsync((offsets, exception) -> {
                if (ObjectUtil.isNotNull(exception)) {
                    log.info("手动提交offset成功: {}", offsets.toString());
                } else {
                    log.error("手动提交offset失败", exception);
                }
            });
        }
        return jsonArr;
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

    @Async
    public JSONArray pullTestObjFromKafka(List<String> topics) {
        try {
            return pullInfoFromKafka(topics);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
