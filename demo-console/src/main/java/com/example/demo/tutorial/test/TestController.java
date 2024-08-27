package com.example.demo.tutorial.test;

import com.example.demo.controller.BaseController;
import com.example.demo.tutorial.test.entity.Student;
import com.example.demo.tutorial.test.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 11:03
 * @Version: 1.0
 */
@Api(tags = "测试控制器", value = "描述信息")
@Controller
@RequestMapping
public class TestController extends BaseController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/index")
    public String index(HttpServletRequest request, ModelMap map) {
        map.put("title", "主菜单");
        map.put("msg", "标题");
        return this.getView("index", map);
    }

    @ApiOperation("测试方法")
    @GetMapping("/dbtest")
    public void dbTest() {
        List<Student> students = studentService.queryStudents();
        students.forEach(System.out::println);
    }

    @GetMapping("/multidbIndex")
    public String multiDbIndex(HttpServletRequest request, ModelMap map) {
        return this.getView("multidb/index", map);
    }
}
