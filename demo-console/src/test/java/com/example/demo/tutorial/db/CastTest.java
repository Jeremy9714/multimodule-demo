package com.example.demo.tutorial.db;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.tutorial.test.service.CastEntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/16 8:46
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class CastTest {

    private final Logger logger = LoggerFactory.getLogger(CastTest.class);

    @Autowired
    private CastEntityService castEntityService;

    @Test
    public void test1() {
        List<Map<String, Object>> allData = castEntityService.getAll();
        for (Map<String, Object> data : allData) {
            JSONObject dataObj = (JSONObject) JSONObject.toJSON(data);
            JSONObject json = new JSONObject();
            json.put("id", dataObj.get("id"));
            json.put("type", dataObj.get("type"));
            logger.info("参数为{}", json);
        }
    }
    
    @Test
    public void test2(){
        List<Map<String, Object>> allData = castEntityService.getAll();
        for (Map<String, Object> data : allData) {
            JSONObject dataObj = (JSONObject) JSONObject.toJSON(data);
            JSONObject json = new JSONObject();
            json.put("id", dataObj.get("id"));
            json.put("type", dataObj.getIntValue("type"));
//            json.put("type", Integer.parseInt(dataObj.getString("type")));
            logger.info("参数为{}", json);
        }
    }
    
    @Test
    public void test3(){
        List<Map<String, Object>> allData = castEntityService.getAll();
        for (Map<String, Object> data : allData) {
            JSONObject dataObj = (JSONObject) JSONObject.toJSON(data);
            JSONObject json = new JSONObject();
            json.put("id", dataObj.get("id"));
            json.put("type", dataObj.getIntValue("type"));
            json.put("name", dataObj.get("name"));
            logger.info("参数为{}", json);
        }
    }
}
