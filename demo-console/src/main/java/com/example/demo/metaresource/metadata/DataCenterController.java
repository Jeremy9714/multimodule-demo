package com.example.demo.metaresource.metadata;

import com.example.demo.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/14 9:07
 * @Version: 1.0
 */
@Controller
@RequestMapping("/datacenter")
public class DataCenterController extends BaseController {

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return this.getView("/metaresource/metadata/datacenter/manage", map);
    }
    
    
}
