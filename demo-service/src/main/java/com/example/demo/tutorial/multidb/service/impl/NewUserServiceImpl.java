package com.example.demo.tutorial.multidb.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.multidb.service.NewUserService;
import com.example.demo.tutorial.platform.dao.NewUserDao;
import com.example.demo.tutorial.platform.entity.NewUser;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/20 9:43
 * @Version: 1.0
 */
@Service
public class NewUserServiceImpl extends ServiceImpl<NewUserDao, NewUser> implements NewUserService {
}
