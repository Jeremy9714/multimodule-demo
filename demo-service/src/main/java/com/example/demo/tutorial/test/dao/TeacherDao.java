package com.example.demo.tutorial.test.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.test.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/23 8:52
 * @Version: 1.0
 */
@Repository
public interface TeacherDao extends BaseMapper<Teacher> {

    List<Teacher> getAllTeacher(Map<String, Object> paramMap);
}
