package com.example.demo.tutorial.login.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.login.entity.UserEntity;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 9:50
 * @Version: 1.0
 */
public interface LoginService extends IService<UserEntity> {

    UserEntity userLogin(String username, String password);
}
