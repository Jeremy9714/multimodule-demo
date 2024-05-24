package com.example.demo.tutorial.es.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 10:30
 * @Version: 1.0
 */
@Service
public class SearchService {

    @Resource
    private RestHighLevelClient highLevelClient;
}
