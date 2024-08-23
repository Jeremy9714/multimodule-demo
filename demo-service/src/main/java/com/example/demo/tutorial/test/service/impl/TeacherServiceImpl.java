package com.example.demo.tutorial.test.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.test.dao.TeacherDao;
import com.example.demo.tutorial.test.entity.Teacher;
import com.example.demo.tutorial.test.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 8:55
 * @Version: 1.0
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Override
    public List<Teacher> getAllTeacher(Map<String, Object> paramMap) {
        return baseMapper.getAllTeacher(paramMap);
    }
}
