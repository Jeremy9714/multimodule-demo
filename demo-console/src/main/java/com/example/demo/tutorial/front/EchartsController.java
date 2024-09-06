package com.example.demo.tutorial.front;

import com.example.demo.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/17 14:41
 * @Version: 1.0
 */
@RequestMapping("/echarts")
@Controller
public class EchartsController extends BaseController {

    @GetMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return getView("/echarts/index", map);
    }
}
