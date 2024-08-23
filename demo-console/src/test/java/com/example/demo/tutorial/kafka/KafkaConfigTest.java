package com.example.demo.tutorial.kafka;

import com.example.demo.config.TopicAutoGenerateConfig;
import com.example.demo.console.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 10:04
 * @Version: 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class KafkaConfigTest {

    @Autowired(required = false)
    private KafkaProperties properties;

    @Value("${kafka.broker.address:}")
    private String brokerAddress;

    public AdminClient adminClient;
    
    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @PostConstruct
    public void init() {
//        if (properties == null) {
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        adminClient = AdminClient.create(props);
//        } else {
//            adminClient = AdminClient.create(properties.buildAdminProperties());
//        }
    }

    @Test
    public void createTopics() {
        if (adminClient != null) {
            try {
                List<NewTopic> topicList = new ArrayList<>();
                topicList.add(new NewTopic("test-topic-4", 1, (short) 1));
                adminClient.createTopics(topicList);
                log.info("======创建完成======");
            } catch (Exception e) {
                log.error("创建失败", e);
            } finally {
                adminClient.close();
            }
        }
    }
}
