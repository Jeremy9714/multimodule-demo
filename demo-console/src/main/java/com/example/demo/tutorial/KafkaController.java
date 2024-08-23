package com.example.demo.tutorial;

import com.example.demo.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 9:19
 * @Version: 1.0
 */
@RequestMapping("/kafka")
@RestController
public class KafkaController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send/{topic}/{msg}")
    public void sendMsg(@PathVariable("topic") String topic, @PathVariable("msg") String msg) {
        kafkaTemplate.send(topic, msg);
    }

    @KafkaListener(id = "webGroup", topics = {"test-topic"})
    public void listen(String msg) {
        logger.info("======kafka msg: {}", msg);
    }
}
