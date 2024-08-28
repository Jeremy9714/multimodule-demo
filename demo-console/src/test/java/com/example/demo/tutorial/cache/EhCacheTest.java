package com.example.demo.tutorial.cache;

import com.example.demo.console.Application;
import com.example.demo.tutorial.test.entity.Student;
import com.example.demo.tutorial.test.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/8/28 14:44
 * @Version: 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class EhCacheTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void test1() {
        String name = "n1";
        List<Student> studentList = studentService.queryStudentByName(name);
        int flag = studentService.updateStudentById(new Student() {{
            setId("1");
            setAge(30);
            setName("n3");
        }});
        if (flag == 0) {
            log.error("更新出错");
            throw new RuntimeException("更新出错");
        }
    }

}
