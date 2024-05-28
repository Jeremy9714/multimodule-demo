package com.example.demo.tutorial.es;

import com.example.demo.console.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 16:06
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class EsJestTest {

    @Autowired
    private StudentSearchService studentSearchService;

    @Test
    public void test1() {
        BigInteger count = studentSearchService.getStudentSortedByNameAndAge("J*");
        System.out.println("查询结果: " + count);
    }
}
