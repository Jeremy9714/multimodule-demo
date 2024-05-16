package com.example.demo.tutorial.multidb;

import com.example.demo.controller.BaseController;
import com.example.demo.tutorial.multidb.entity.EntityA;
import com.example.demo.tutorial.multidb.entity.EntityB;
import com.example.demo.tutorial.multidb.service.CatalogGroupService;
import com.example.demo.tutorial.multidb.service.CatalogService;
import com.example.demo.tutorial.multidb.service.MultiDSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 多数据源测试控制器
 * @Author: zhangchy05 on 2024/5/15 10:35
 * @Version: 1.0
 */
@Slf4j
@RequestMapping("/multidb")
@RestController
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

    @GetMapping("/test")
    public void testMultiDs() {
        List<EntityA> dataFromOne = multiDSService.getDataFromOne();
        dataFromOne.forEach(System.out::println);
        List<EntityB> dataFromTwo = multiDSService.getDataFromTwo();
        dataFromTwo.forEach(System.out::println);
    }
}
