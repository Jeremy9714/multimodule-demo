package com.example.demo.tutorial.es;

import com.example.demo.constant.AjaxResult;
import com.example.demo.tutorial.es.service.OperateIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 9:39
 * @Version: 1.0
 */
@RestController
@RequestMapping("/es/index")
public class IndexController {

    @Autowired
    private OperateIndexService operateIndexService;

    @GetMapping("/create")
    public AjaxResult<Boolean> createIndex(@RequestParam String indexName) {
        return AjaxResult.success(operateIndexService.createIndex(indexName));
    }

//    @GetMapping("/exist")
//    public AjaxResult<Boolean> existIndex(@RequestParam String indexName) {
//        return AjaxResult.success(operateIndexService.isIndexExists(indexName));
//    }

    @GetMapping("/delete")
    public AjaxResult<Boolean> deleteIndex(@RequestParam String indexName) {
        return AjaxResult.success(operateIndexService.deleteIndex(indexName));
    }

}
