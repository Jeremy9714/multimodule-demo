package com.example.demo.tutorial.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.tutorial.test.entity.Student;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 11:06
 * @Version: 1.0
 */
public interface StudentService extends IService<Student> {
    List<Student> queryStudents();

    List<Student> queryStudentByName(String name);

    Integer updateStudentById(Student student);
}
