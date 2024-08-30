package com.example.demo.tutorial.mockito.service;

import com.example.demo.tutorial.mockito.dao.MockitoDao;
import com.example.demo.tutorial.mockito.entity.MockitoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/30 16:08
 * @Version: 1.0
 */
@Service
public class MockitoServicePlus {

    @Value("${mockito.test.value:mk1}")
    private String mockitoValue;

    @Autowired
    private MockitoDao mockitoDao;

//    public final MockitoDao mockitoDao;

//    public MockitoServicePlus(MockitoDao mockitoDao) {
//        this.mockitoDao = mockitoDao;
//    }

    public List<MockitoEntity> getAll() {
        return mockitoDao.getAll();
    }

    public void printMsg(String msg) {
        System.out.println("======msg: " + msg);
        System.out.println("======value: " + mockitoValue);
    }
}
