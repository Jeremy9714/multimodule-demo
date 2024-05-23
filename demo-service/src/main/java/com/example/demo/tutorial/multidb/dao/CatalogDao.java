package com.example.demo.tutorial.multidb.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.multidb.entity.Catalog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 14:38
 * @Version: 1.0
 */
@Repository
public interface CatalogDao extends BaseMapper<Catalog> {
    List<Catalog> selectCatalogList();
}
