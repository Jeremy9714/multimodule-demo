package com.example.demo.tutorial.es.service;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 8:50
 * @Version: 1.0
 */
@Service
public class OperateIndexService {

    @Autowired
    private RestHighLevelClient highLevelClient;

    public boolean createIndex(String indexName) {
        boolean acknowledged = false;
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("properties").startObject()
                    .field("firstname").startObject().field("type", "keyword").endObject()
                    .field("lastname").startObject().field("type", "keyword").endObject()
                    .field("content").startObject().field("type", "keyword").endObject()
                    .field("age").startObject().field("type", "integer").endObject()
                    .endObject()
                    .endObject();
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            createIndexRequest.mapping("_doc", xContentBuilder);
            CreateIndexResponse createIndexResponse = highLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            acknowledged = createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }

    public boolean isIndexExists(String indexName) {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.humanReadable(true);
        boolean exists = false;
        try {
            exists = highLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean deleteIndex(String indexName) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
        // 忽略索引不存在的情况，否则报错
        deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        boolean acknowledged = false;
        try {
            AcknowledgedResponse deleteIndexResponse = highLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = deleteIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acknowledged;
    }
}
