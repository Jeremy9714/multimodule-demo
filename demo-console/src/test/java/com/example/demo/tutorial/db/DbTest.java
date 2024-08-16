package com.example.demo.tutorial.db;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.console.Application;
import com.example.demo.tutorial.multidb.entity.Catalog;
import com.example.demo.tutorial.multidb.service.CatalogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/12 16:44
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class DbTest {

    @Autowired
    CatalogService catalogService;

    @Test
    public void test1() {
        EntityWrapper<Catalog> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(1) as sum,org_code as orgCode");
        wrapper.groupBy("org_code");
        String sqlSegment = wrapper.getSqlSegment();
        System.out.println(sqlSegment);
        List<Map<String, Object>> list = catalogService.selectMaps(wrapper);
        Map<String, Object> map = new HashMap<>();
        list.forEach(item -> map.put(item.get("orgCode").toString(), item.get("sum")));
        System.out.println(map);
    }

    @Test
    public void insertTest1() {
        Catalog catalog = new Catalog();
        catalog.setId("1243");
        catalog.setCataCode("23423423");
        catalogService.insert(catalog);
    }

    @Test
    public void test2() {
        EntityWrapper<Catalog> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("count(1) as value, org_code as orgCode");
        wrapper.groupBy("org_code");
        wrapper.orderBy("value", false);
        List<Map<String, Object>> list = catalogService.selectMaps(wrapper);
        list.forEach(System.out::println);
    }
}
