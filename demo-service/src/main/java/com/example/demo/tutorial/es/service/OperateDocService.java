package com.example.demo.tutorial.es.service;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.constant.AjaxResult;
import com.example.demo.tutorial.es.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 9:52
 * @Version: 1.0
 */
@Service
@Slf4j
public class OperateDocService {

    @Resource
    private RestHighLevelClient highLevelClient;

    public AjaxResult<String> insertDoc(User user, String indexName, String docId) {
        IndexRequest indexRequest = new IndexRequest(indexName);
        // 设置文档id，否则自动生成唯一id
        indexRequest.id(docId);
        // 设置文档数据和格式
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        indexRequest.type("_doc");
        try {
            IndexResponse indexResponse = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            if (ObjectUtil.isNotNull(indexResponse)) {
                String id = indexResponse.getId();
                DocWriteResponse.Result result = indexResponse.getResult();
                if (ObjectUtil.equals(DocWriteResponse.Result.CREATED, result)) {
                    return new AjaxResult<>(200, "新增文档成功", id);
                } else {
                    return new AjaxResult<>(200, "更新文档成功", id);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return AjaxResult.fail(docId);
    }

    public AjaxResult<String> getDoc(String indexName, String docId) {
        GetRequest getRequest = new GetRequest(indexName, "_doc", docId);
        try {
            GetResponse getResponse = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return AjaxResult.success(getResponse.getSourceAsString());
            } else {
                return AjaxResult.fail("文档不存在");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return AjaxResult.fail(docId);
    }

    public AjaxResult<String> updateDoc(String indexName, String docId, String fieldName, String fieldValue) {
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field(fieldName, fieldValue)
                    .endObject();
            UpdateRequest updateRequest = new UpdateRequest(indexName, "_doc", docId);
            updateRequest.doc(xContentBuilder);
            // 不能存在则添加
            updateRequest.docAsUpsert(true);
            // response中包含文档内容
            updateRequest.fetchSource(true);
            UpdateResponse updateResponse = highLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            GetResult getResult = updateResponse.getGetResult();
            if (getResult.isExists()) {
                return AjaxResult.success(getResult.sourceAsString());
            } else {
                return AjaxResult.fail("更新文档失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.fail(docId);
    }

    public AjaxResult<String> deleteDoc(String indexName, String docId) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName, "_doc", docId);
        try {
            DeleteResponse deleteResponse = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if (ObjectUtil.isNotNull(deleteResponse)) {
                DocWriteResponse.Result result = deleteResponse.getResult();
                if (ObjectUtil.equals(DocWriteResponse.Result.DELETED, result)) {
                    return AjaxResult.success("文档删除成功");
                } else {
                    return AjaxResult.fail("文档不存在");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.fail(docId);
    }
}
