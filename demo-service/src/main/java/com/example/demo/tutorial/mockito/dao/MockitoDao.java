package com.example.demo.tutorial.mockito.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 14:40
 * @Version: 1.0
 */
@Repository
public interface MockitoDao extends BaseMapper<MockitoEntity> {

    List<MockitoEntity> getAll();

    List<MockitoEntity> getEntity(Map<String, Object> paramMap);
}
