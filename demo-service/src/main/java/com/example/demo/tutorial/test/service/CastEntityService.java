package com.example.demo.tutorial.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.test.entity.CastEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/16 8:40
 * @Version: 1.0
 */
public interface CastEntityService extends IService<CastEntity> {
    
    List<Map<String,Object>> getAll();
}
