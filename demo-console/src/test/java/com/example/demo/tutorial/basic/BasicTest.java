package com.example.demo.tutorial.basic;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.tutorial.multidb.entity.Catalog;
import com.example.demo.tutorial.multidb.service.CatalogService;
import com.example.demo.tutorial.test.entity.PatternEntity;
import com.example.demo.tutorial.test.service.PatternEntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/20 13:53
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class BasicTest {

    @Autowired
    PatternEntityService patternEntityService;

    @Autowired
    CatalogService catalogService;

    @Test
    public void test1() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("gender", null);
//        jsonObject.put("gender", "");
        String gender = Optional.ofNullable(jsonObject.getString("gender")).orElse("0");
        System.out.println(gender);
    }

    @Test
    public void test2() {
        JSONObject obj = new JSONObject();
        obj.put("gender", "");
        String gender = Optional.ofNullable(obj.getString("gender")).orElse("0");
        System.out.println(gender);
    }

    @Test
    public void test3() {
        PatternEntity entity = new PatternEntity();
        entity.setId("123");
        entity.setName("abc");
        entity.setIdentityNum("321282200006013636");
        patternEntityService.printDetail(entity);
    }

    @Test
    public void test4() {
        Catalog catalog = catalogService.selectById("123123123123123");
        System.out.println(catalog);
    }

    @Test
    public void test5() {
        List<String> strings = new ArrayList<>();
        Arrays.asList(1,2,3);
        strings.add("B");
        strings.add("C");
        strings.add("D");
        strings.forEach(System.out::println);

        System.out.println("======================");

        strings.add(0, "A");
        strings.forEach(System.out::println);
    }
}
