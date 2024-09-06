package com.example.demo.tutorial.test.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.tutorial.test.dao.StudentDao;
import com.example.demo.tutorial.test.entity.Student;
import com.example.demo.tutorial.test.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/15 11:07
 * @Version: 1.0
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> queryStudents() {
        return studentDao.selectList(new EntityWrapper<>());
    }

    //    @Cacheable(value = "mycache", key = "#name")
    @Override
    public List<Student> queryStudentByName(String name) {
        EntityWrapper<Student> wrapper = new EntityWrapper<>();
        wrapper.eq("name", name);
        return baseMapper.selectList(wrapper);
    }

    //    @CacheEvict(value = "mycache", allEntries = true)
    @Override
    public Integer updateStudentById(Student student) {
        return baseMapper.updateById(student);
    }

    @Override
    public List<Student> queryByMap(Map<String, Object> paramMap) {
        String id = (String) paramMap.getOrDefault("id", "");
        String name = (String) paramMap.getOrDefault("name", "");
        EntityWrapper<Student> wrapper = new EntityWrapper<>();
        wrapper.where("`name` like '%" + name + "%'");
        wrapper.eq("id", id);
        return studentDao.selectList(wrapper);
    }

}
