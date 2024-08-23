package com.example.demo.tutorial.kafka;

import com.example.demo.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * kafka事务
     *
     * @param topic
     * @param msg
     */
    @GetMapping("/sendTx/{topic}/{msg}")
    public void sendTxMsg(@PathVariable("topic") String topic, @PathVariable("msg") String msg) {
        kafkaTemplate.executeInTransaction(t -> {
            t.send(topic, "v1");
            if ("error".equals(msg)) {
                // 回滚
                throw new RuntimeException("failed");
            }
            t.send(topic, "v2");
            return true;
        });
    }

    /**
     * kafka事务
     *
     * @param topic
     * @param msg
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @GetMapping("/sendTx2/{topic}/{msg}")
    public void sendTxMsg2(@PathVariable("topic") String topic, @PathVariable("msg") String msg) {
        kafkaTemplate.send(topic, "v3");
        if ("error".equals(msg)) {
            // 回滚
            throw new RuntimeException("failed");
        }
        kafkaTemplate.send(topic, "v4");
    }

    @KafkaListener(id = "webGroup", topics = {"test-topic"})
    public void listen(String msg, Acknowledgment ack) {
        logger.info("======kafka msg: {}", msg);
        if ("k1".equals(msg)) {
            // 手动提交偏移量
            ack.acknowledge();
        }
    }

}
