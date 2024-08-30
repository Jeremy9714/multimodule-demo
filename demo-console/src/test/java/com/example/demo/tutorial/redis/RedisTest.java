package com.example.demo.tutorial.redis;

import com.example.demo.console.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;


/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:00
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired(required = false)
    ValueOperations<String, String> redisOperations;

    @Test
    public void test1() {
        String value = redisTemplate.opsForValue().get("k1");
        System.out.println("======" + value + "======");

        Set<String> set = redisTemplate.opsForSet().members("aset");
        set.forEach(System.out::println);
    }

    @Test
    public void test2() {
        String value = redisOperations.get("k1");
        System.out.println(value);
    }
}
