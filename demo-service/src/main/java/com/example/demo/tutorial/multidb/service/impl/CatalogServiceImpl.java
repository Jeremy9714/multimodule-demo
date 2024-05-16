package com.example.demo.tutorial.multidb.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.multidb.dao.CatalogDao;
import com.example.demo.tutorial.multidb.entity.Catalog;
import com.example.demo.tutorial.multidb.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:40
 * @Version: 1.0
 */
@Slf4j
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogDao, Catalog> implements CatalogService {
}
