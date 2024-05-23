package com.example.demo.tutorial.multidb.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.multidb.dao.CatalogDao;
import com.example.demo.tutorial.multidb.entity.Catalog;
import com.example.demo.tutorial.multidb.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:40
 * @Version: 1.0
 */
@Slf4j
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogDao, Catalog> implements CatalogService {

    @Autowired
    private CatalogDao catalogDao;

    @Override
    public Page<Catalog> getCatalogList(Page<Catalog> page, Map<String, Object> paramMap) {
        List<Catalog> catalogList = catalogDao.selectCatalogList();
        page.setRecords(catalogList);
        return page;
    }
}
