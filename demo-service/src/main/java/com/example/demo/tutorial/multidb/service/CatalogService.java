package com.example.demo.tutorial.multidb.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.multidb.entity.Catalog;

import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:39
 * @Version: 1.0
 */
public interface CatalogService extends IService<Catalog> {
    Page<Catalog> getCatalogList(Page<Catalog> page, Map<String, Object> paramMap);
}
