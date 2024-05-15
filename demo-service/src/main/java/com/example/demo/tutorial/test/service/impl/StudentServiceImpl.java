package com.example.demo.tutorial.test.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.test.dao.StudentDao;
import com.example.demo.tutorial.test.entity.Student;
import com.example.demo.tutorial.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 11:07
 * @Version: 1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> queryStudents() {
        return studentDao.selectList(new EntityWrapper<>());
    }
}
