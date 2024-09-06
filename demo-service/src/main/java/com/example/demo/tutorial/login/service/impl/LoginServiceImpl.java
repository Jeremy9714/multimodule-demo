package com.example.demo.tutorial.login.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.login.dao.LoginDao;
import com.example.demo.tutorial.login.entity.UserEntity;
import com.example.demo.tutorial.login.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 9:52
 * @Version: 1.0
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginDao, UserEntity> implements LoginService {

    @Override
    public UserEntity userLogin(String username, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(username);
        userEntity.setPassword(password);
        return baseMapper.selectOne(userEntity);
    }
}
