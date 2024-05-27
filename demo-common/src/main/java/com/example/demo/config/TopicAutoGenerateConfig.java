package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/27 9:57
 * @Version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "kafka.init", value = "enable", havingValue = "1")
public class TopicAutoGenerateConfig {

    private final GenericWebApplicationContext context;

    @Value("${kafka.init.partitionnum:3}")
    private Integer defaultPartitionNum;

    @Value("${kafka.init.replicationfactornum:2}")
    private Short defaultReplicationFactorNum;

    @Value("${kafka.test-topic:}")
    private String defaultTopicName;

    public TopicAutoGenerateConfig(GenericWebApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        initializeBeans(getTopics());
    }

    private void initializeBeans(List<Topic> topics) {
        topics.forEach(t -> {
            context.registerBean(t.name, NewTopic.class, t::toNewTopic);
        });
    }

    private List<Topic> getTopics() {
        List<Topic> topics = new ArrayList<>();
        Topic testTopic = new Topic(defaultTopicName, defaultPartitionNum, defaultReplicationFactorNum);
        topics.add(testTopic);
        return topics;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Topic {
        String name;
        Integer partitionNums;
        Short replicationFactor;

        NewTopic toNewTopic() {
            return new NewTopic(this.name, this.partitionNums, this.replicationFactor);
        }
    }

}
