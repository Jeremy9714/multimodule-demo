package com.example.demo.tutorial.front;

import com.example.demo.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/7/18 14:09
 * @Version: 1.0
 */
@Controller
@RequestMapping("/front")
public class FrontController extends BaseController {

    @GetMapping("/index")
    public String index(ModelMap map) {
        return this.getView("front/index", map);
    }
}
