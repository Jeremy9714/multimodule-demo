package com.example.demo.tutorial.web;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.utils.web.HttpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/30 15:14
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class HttpClientTest {

    @Test
    public void httpGetTest1() {
        String url = "http://localhost:9999/demo/tempdata/v1/datas";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "n1");
        String response = HttpClientUtils.doGet(url, paramMap);
        System.out.println(response);
    }
}
