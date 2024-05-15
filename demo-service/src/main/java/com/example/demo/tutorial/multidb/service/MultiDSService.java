package com.example.demo.tutorial.multidb.service;

import com.example.demo.tutorial.multidb.entity.EntityA;
import com.example.demo.tutorial.multidb.entity.EntityB;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 10:22
 * @Version: 1.0
 */
public interface MultiDSService {
    
    List<EntityA> getDataFromOne();
    
    List<EntityB> getDataFromTwo();
}
