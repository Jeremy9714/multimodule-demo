package com.example.demo.tutorial.test.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.tutorial.test.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 11:05
 * @Version: 1.0
 */
@Repository
public interface StudentDao extends BaseMapper<Student> {
}
