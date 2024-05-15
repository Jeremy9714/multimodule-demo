package com.example.demo.tutorial.multidb.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.multidb.entity.EntityB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 10:19
 * @Version: 1.0
 */
@Mapper
public interface EntityBDao extends BaseMapper<EntityB> {

    List<EntityB> queryData();

}
