package com.example.demo.tutorial.test;

import com.example.demo.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 11:03
 * @Version: 1.0
 */
@Controller
@RequestMapping
public class TestController extends BaseController {

    @GetMapping("/index")
    public String index(HttpServletRequest request, ModelMap map) {
        map.put("title", "主菜单");
        map.put("msg", "标题");
        return this.getView("index", map);
    }
}
