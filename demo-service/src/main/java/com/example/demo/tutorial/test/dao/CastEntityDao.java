package com.example.demo.tutorial.test.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.test.entity.CastEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/16 8:39
 * @Version: 1.0
 */
@Repository
public interface CastEntityDao extends BaseMapper<CastEntity> {
    
    List<Map<String,Object>> getAll();
}
