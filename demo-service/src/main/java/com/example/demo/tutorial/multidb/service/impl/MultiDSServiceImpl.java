package com.example.demo.tutorial.multidb.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.tutorial.multidb.dao.EntityADao;
import com.example.demo.tutorial.multidb.dao.EntityBDao;
import com.example.demo.tutorial.multidb.entity.EntityA;
import com.example.demo.tutorial.multidb.entity.EntityB;
import com.example.demo.tutorial.multidb.service.MultiDSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 10:23
 * @Version: 1.0
 */
@Slf4j
@Service
public class MultiDSServiceImpl implements MultiDSService {
    @Autowired
    private EntityADao entityADao;

    @Autowired
    private EntityBDao entityBDao;
    
    @DS("one")
    @Override
    public List<EntityA> getDataFromOne() {
        return entityADao.queryData();
    }

    @DS("two")
    @Override
    public List<EntityB> getDataFromTwo() {
        return entityBDao.queryData();
    }
}
