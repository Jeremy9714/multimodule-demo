package com.example.demo.tutorial.utils;

import com.example.demo.console.Application;
import com.example.demo.constant.kafka.TestData;
import com.example.demo.utils.bean.CommonBeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/31 8:52
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class UtilsTest {

    @Test
    public void beanTest1() {
        List<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("code", "1");
        map1.put("name", "n1");
        map1.put("age", 18);
        maps.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("code", "2");
        map2.put("name", "n2");
        map2.put("age", 18);
        maps.add(map2);
        List<TestData> testDatas = CommonBeanUtils.mapsToObjects(maps, TestData.class);
        testDatas.forEach(System.out::println);
    }
}
