package com.example.demo.tutorial.multidb;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.controller.BaseController;
import com.example.demo.tutorial.multidb.entity.Catalog;
import com.example.demo.tutorial.multidb.entity.EntityA;
import com.example.demo.tutorial.multidb.entity.EntityB;
import com.example.demo.tutorial.multidb.service.CatalogGroupService;
import com.example.demo.tutorial.multidb.service.CatalogService;
import com.example.demo.tutorial.multidb.service.MultiDSService;
import com.example.demo.utils.PageConverterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 多数据源测试控制器
 * @Author: zhangchy05 on 2024/5/15 10:35
 * @Version: 1.0
 */
@Slf4j
@RequestMapping("/multidb")
@Controller
public class MultiDbController extends BaseController {

    @Autowired
    private MultiDSService multiDSService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CatalogGroupService catalogGroupService;

    @GetMapping("/index")
    public String getIndex(HttpServletRequest request, ModelMap map) {
        return this.getView("/multidb/index", map);
    }

    @PostMapping("/getCatalogList")
    public void getCatalogList(HttpServletRequest request, HttpServletResponse response) {
        int offset = this.getParaInt("start");
        int limit = this.getParaInt("length");
        int draw = this.getParaInt("draw");
        int current = (offset + limit) / limit;
        Page<Catalog> page = new Page<>();
        Map<String, Object> paramMap = new HashMap<>();
        String cataTitle = this.getPara("cataTitle");
        String groupId = this.getPara("groupId");
        paramMap.put("cataTitle", cataTitle);
        paramMap.put("groupId", groupId);
        page.setCurrent(current);
        page.setSize(limit);
        page = catalogService.getCatalogList(page, null);
        this.renderJson(response, PageConverterUtils.toDataTable(page, draw).toString());
    }

    @PostMapping("/getCatalogGroup")
    public void getCatalogGroups(HttpServletRequest request, HttpServletResponse response) {
    }

    @GetMapping("/view")
    public String getCatalogView(HttpServletRequest request, ModelMap map) {
        return this.getView("/multidb/detail", map);
    }

    @PostMapping("/detail")
    public void getCatalogDetail(HttpServletRequest request, HttpServletResponse response) {
    }

    @ResponseBody
    @PostMapping("/checkLogin")
    public JSONObject checkLogin(HttpServletRequest request, HttpServletResponse response) {
        JSONObject resultObj = new JSONObject();
        resultObj.put("code", 200);
        resultObj.put("msg", "已登录");
        return resultObj;
    }

    @GetMapping("/test")
    public void testMultiDs() {
        List<EntityA> dataFromOne = multiDSService.getDataFromOne();
        dataFromOne.forEach(System.out::println);
        List<EntityB> dataFromTwo = multiDSService.getDataFromTwo();
        dataFromTwo.forEach(System.out::println);
    }
}
