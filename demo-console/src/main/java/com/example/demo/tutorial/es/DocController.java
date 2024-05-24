package com.example.demo.tutorial.es;

import com.example.demo.constant.AjaxResult;
import com.example.demo.tutorial.es.entity.User;
import com.example.demo.tutorial.es.service.OperateDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 10:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/es/doc")
public class DocController {

    @Autowired
    private OperateDocService operateDocService;

    @PostMapping("/insert")
    public AjaxResult<String> insertDoc(@RequestBody User user, String indexName, String docId) {
        return operateDocService.insertDoc(user, indexName, docId);
    }

    @PostMapping("/query")
    public AjaxResult<String> queryDoc(@RequestParam String indexName, @RequestParam String docId) {
        return operateDocService.getDoc(indexName, docId);
    }

    @PostMapping("/update")
    public AjaxResult<String> updateDoc(@RequestParam String indexName, @RequestParam String docId,
                                        @RequestParam String fieldName, @RequestParam String fieldValue) {
        return operateDocService.updateDoc(indexName, docId, fieldName, fieldValue);
    }

    @PostMapping("/delete")
    public AjaxResult<String> deleteDoc(@RequestParam String indexName, @RequestParam String docId) {
        return operateDocService.deleteDoc(indexName, docId);
    }
}
