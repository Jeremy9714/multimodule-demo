package com.example.demo.tutorial.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.test.entity.PatternEntity;

import javax.validation.Valid;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/24 9:39
 * @Version: 1.0
 */
public interface PatternEntityService extends IService<PatternEntity> {

    void printDetail(@Valid PatternEntity entity);
}
