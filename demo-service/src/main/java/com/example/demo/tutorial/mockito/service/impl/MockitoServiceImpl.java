package com.example.demo.tutorial.mockito.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.mockito.dao.MockitoDao;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;
import com.example.demo.tutorial.mockito.service.MockitoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:39
 * @Version: 1.0
 */
@Service
public class MockitoServiceImpl extends ServiceImpl<MockitoDao, MockitoEntity> implements MockitoService {

    @Value("${mockito.test.value:mk1}")
    private String mockitoValue;

    @Override
    public MockitoEntity getEntity(Map<String, Object> paramMap) {
        List<MockitoEntity> list = baseMapper.getEntity(paramMap);
        return list != null ? list.get(0) : null;

    }

    @Override
    public List<MockitoEntity> getAll() {
        return baseMapper.getAll();
    }

    @Override
    public void returnNothing(String input) {
        System.out.println("======input: " + input);
        System.out.println("======mockitoValue: " + mockitoValue);
    }
}
