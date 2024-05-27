package com.example.demo.tutorial.es;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.constant.es.AjaxResult;
import com.example.demo.tutorial.es.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 14:39
 * @Version: 1.0
 */
@RestController
@RequestMapping("/es/search")
public class SearchController {

    private static final String INDEX_NAME = "fisher";

    @Autowired
    private SearchService searchService;

    @GetMapping("/example")
    public AjaxResult<JSONArray> searchExample() {
        return searchService.searchCase(INDEX_NAME);
    }

    @GetMapping("/term")
    public AjaxResult<JSONArray> termSearch() {
        return searchService.termSearch(INDEX_NAME);
    }

    @GetMapping("/match")
    public AjaxResult<JSONArray> matchSearch() {
        return searchService.matchSearch(INDEX_NAME);
    }

    @GetMapping("/fuzzy")
    public AjaxResult<JSONArray> fuzzySearch() {
        return searchService.fuzzySearch(INDEX_NAME);
    }

    @GetMapping("/bool")
    public AjaxResult<JSONArray> boolSearch() {
        return searchService.boolMatch(INDEX_NAME);
    }

}
