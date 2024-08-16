package com.example.demo.tutorial.test.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.test.dao.CastEntityDao;
import com.example.demo.tutorial.test.entity.CastEntity;
import com.example.demo.tutorial.test.service.CastEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/16 8:41
 * @Version: 1.0
 */
@Service
public class CastEntityServiceImpl extends ServiceImpl<CastEntityDao, CastEntity> implements CastEntityService {

    @Autowired
    private CastEntityDao castEntityDao;

    @Override
    public List<Map<String, Object>> getAll() {
        return castEntityDao.getAll();
    }
}
