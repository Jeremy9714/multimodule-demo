package com.example.demo.tutorial.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/24 15:01
 * @Version: 1.0
 */
@Slf4j
@RequestMapping("/kafka3")
@RestController
public class KafkaController3 {

    @Value("${kafka.broker.address:}")
    private String brokerAddress;

    @Bean
    public Producer<String, String> producer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my_tx_id");
        return new KafkaProducer<>(properties);
    }

    @Autowired
    private Producer<String, String> producer;

    @RequestMapping("/tx1")
    public void doTransactionJob() {
        producer.initTransactions();
        try {
            producer.beginTransaction();
            producer.send(new ProducerRecord<>("basic-topic", 0, "key1", "value1"));
            producer.send(new ProducerRecord<>("basic-topic", 0, "key2", "value2"));
            producer.commitTransaction();
        } catch (Exception e) {
            producer.abortTransaction();
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }


//    @KafkaListener(id = "listener1", topics = {"basic-topic"})
    public void listener(String msg, Acknowledgment ack) {
        log.info("msg: {}", msg);
        if (StringUtils.isNotBlank(msg)) {
            ack.acknowledge();
        }
    }

}
