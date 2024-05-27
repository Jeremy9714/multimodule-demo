package com.example.demo.tutorial.es;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.constant.es.AjaxResult;
import com.example.demo.tutorial.es.service.AggsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 15:09
 * @Version: 1.0
 */
@RestController
@RequestMapping("/es/aggs")
public class AggsSearchController {

    private final static String INDEX_NAME = "fisher";

    @Autowired
    private AggsSearchService aggsSearchService;

    @PostMapping("/query")
    public AjaxResult<JSONArray> aggsQuery() {
        return aggsSearchService.aggsSearch(INDEX_NAME);
    }
}
