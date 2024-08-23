package com.example.demo.tutorial.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.test.entity.Teacher;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 8:55
 * @Version: 1.0
 */
public interface TeacherService extends IService<Teacher> {

    List<Teacher> getAllTeacher(Map<String, Object> paramMap);
}
