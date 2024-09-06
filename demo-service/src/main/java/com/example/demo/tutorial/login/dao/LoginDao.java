package com.example.demo.tutorial.login.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.login.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 9:47
 * @Version: 1.0
 */
@Repository
public interface LoginDao extends BaseMapper<UserEntity> {

//    UserEntity userLogin(@Param("username") String username, @Param("password") String password);
}
