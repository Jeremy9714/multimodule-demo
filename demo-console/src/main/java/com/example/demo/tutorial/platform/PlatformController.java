package com.example.demo.tutorial.platform;

import com.example.demo.controller.BaseController;
import com.example.demo.tutorial.multidb.service.NewUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/6/20 9:51
 * @Version: 1.0
 */
@RequestMapping("/platform")
@Controller
public class PlatformController extends BaseController {

    @Autowired
    NewUserService newUserService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap map) {
        return this.getView("platform/index", map);
    }

    @PostMapping("/getUserList")
    @ResponseBody
    public void getUserList(HttpServletRequest request, HttpServletResponse response) {
        
    }


}
