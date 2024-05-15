package com.example.demo.tutorial.multidb;

import com.example.demo.tutorial.multidb.entity.EntityA;
import com.example.demo.tutorial.multidb.entity.EntityB;
import com.example.demo.tutorial.multidb.service.MultiDSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: 多数据源测试控制器
 * @Author: zhangchy05 on 2024/5/15 10:35
 * @Version: 1.0
 */
@Slf4j
@RequestMapping("/multidb")
@RestController
public class MultiDbController {

    @Autowired
    private MultiDSService multiDSService;

    @GetMapping("/test")
    public void testMultiDs() {
        List<EntityA> dataFromOne = multiDSService.getDataFromOne();
        dataFromOne.forEach(System.out::println);
        List<EntityB> dataFromTwo = multiDSService.getDataFromTwo();
        dataFromTwo.forEach(System.out::println);
    }
}
