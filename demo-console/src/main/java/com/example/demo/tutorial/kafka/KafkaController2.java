package com.example.demo.tutorial.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 11:15
 * @Version: 1.0
 */
@Slf4j
@RequestMapping("/kafka2")
@RestController
public class KafkaController2 {

    @Bean
    public ConcurrentMessageListenerContainer<String, String> repliesContainer(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        ConcurrentMessageListenerContainer<String, String> repliesContainer = factory.createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(ProducerFactory<String, String> factory, ConcurrentMessageListenerContainer<String, String> repliesContainer) {
        return new ReplyingKafkaTemplate(factory, repliesContainer);
    }

    @Bean("kafkaTemplate2")
    public KafkaTemplate kafkaTemplate(ProducerFactory<String, String> factory) {
        return new KafkaTemplate(factory);
    }

    @Lazy
    @Autowired
    private ReplyingKafkaTemplate replyingTemplate;

    @Lazy
    @Resource(name = "kafkaTemplate2")
    private KafkaTemplate template;

    @GetMapping("/send/{topic}/{msg}")
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendMsg(@PathVariable("topic") String topic, @PathVariable("msg") String msg) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
        RequestReplyFuture<String, String, String> replyFuture = replyingTemplate.sendAndReceive(record);
        ConsumerRecord<String, String> consumerRecord = replyFuture.get();
        log.info("======return value: {}", consumerRecord.value());
    }

    @KafkaListener(id = "webGroup2", topics = {"test-topic2"})
    @SendTo("test-topic3") // 发送响应，支持转发topic
    public String listen(String msg) {
        log.info("======test-topic2: {}", msg);
        return "success";
    }

    @KafkaListener(id = "webGroup3", topics = {"test-topic3"})
    public void listen2(String msg) {
        log.info("======test-topic3: {}", msg);
    }

    //====================监听器生命周期=============================

    @Autowired
    private KafkaListenerEndpointRegistry registry;

    @GetMapping("/send/{msg}")
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendMsgToTestTopic4(@PathVariable String msg) throws Exception {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("test-topic4", msg);
        template.send(record);
    }

    @GetMapping("/stop/{listenerID}")
    public void stop(@PathVariable String listenerID) {
        registry.getListenerContainer(listenerID).pause();
    }

    @GetMapping("/resume/{listenerID}")
    public void resume(@PathVariable String listenerID) {
        registry.getListenerContainer(listenerID).resume();
    }

    @GetMapping("/start/{listenerID}")
    public void start(@PathVariable String listenerID) {
        registry.getListenerContainer(listenerID).start();
    }

    @KafkaListener(id = "webGroup4", topics = "test-topic4", /*关闭自动启动消费*/ autoStartup = "false")
    public String listen3(String msg) {
        log.info("======input value: {}", msg);
        return "successful";
    }
}
