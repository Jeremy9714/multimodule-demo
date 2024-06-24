package com.example.demo.tutorial.test.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.test.dao.PatternEntityDao;
import com.example.demo.tutorial.test.entity.PatternEntity;
import com.example.demo.tutorial.test.service.PatternEntityService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;


/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/24 9:40
 * @Version: 1.0
 */
@Validated
@Service
public class PatternEntityServiceImpl extends ServiceImpl<PatternEntityDao, PatternEntity> implements PatternEntityService {
    @Override
    public void printDetail(@Valid PatternEntity entity) {
        System.out.println("--打印--");
        System.out.println(entity);
    }
}
