package com.example.demo.tutorial.mockito.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:37
 * @Version: 1.0
 */
public interface MockitoService extends IService<MockitoEntity> {

    MockitoEntity getEntity(Map<String, Object> paramMap);

    List<MockitoEntity> getAll();

    void returnNothing(String input);
}
