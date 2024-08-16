package com.example.demo.tutorial.platform.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.platform.entity.NewUser;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/20 9:42
 * @Version: 1.0
 */
@Repository
public interface NewUserDao extends BaseMapper<NewUser> {
}
