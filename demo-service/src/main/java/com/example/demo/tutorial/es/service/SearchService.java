package com.example.demo.tutorial.es.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 10:30
 * @Version: 1.0
 */
@Service
@Slf4j
public class SearchService {

    @Resource
    private RestHighLevelClient highLevelClient;

    /**
     * 处理返回结果中的hits部分
     *
     * @param indexName
     * @param searchSourceBuilder
     * @return
     */
    private AjaxResult<JSONArray> send(String indexName, SearchSourceBuilder searchSourceBuilder) {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(indexName);
            searchRequest.source(searchSourceBuilder);
            SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            JSONArray jsonArray = new JSONArray();
            for (SearchHit hit : hits) {
                String src = hit.getSourceAsString();
                JSONObject srcObj = JSONObject.parseObject(src);
                jsonArray.add(srcObj);
            }
            return AjaxResult.success(jsonArray);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return AjaxResult.fail(null);
    }

    /**
     * _search接口
     *
     * @param indexName
     * @return
     */
    public AjaxResult<JSONArray> searchCase(String indexName) {
        /**
         * 查询条件
         * get indexName/_search
         * {
         *   "from":0,
         *   "size":5,
         *   "query":{
         *       "match_all":{}
         *   },
         *   "_source":["name","lang"],
         *   "sort“:[
         *      {
         *          "stars":"asc"
         *      }
         *   ]
         * }
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        // query查询
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        String[] includeFields = {"name", "lang"};
        // _source字段筛选
        searchSourceBuilder.fetchSource(includeFields, null);
        // 排序
        searchSourceBuilder.sort(new FieldSortBuilder("stars").order(SortOrder.ASC));
        return send(indexName, searchSourceBuilder);
    }

    /**
     * 基于词项的查询
     *
     * @param indexName
     * @return
     */
    public AjaxResult<JSONArray> termSearch(String indexName) {
        /**
         * get indexName/_search
         * {
         *     "query":{
         *         "term":{
         *             "stars": 300
         *         }
         *     }
         * }
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("stars", 300);
        searchSourceBuilder.query(termQueryBuilder);
        return send(indexName, searchSourceBuilder);
    }

    /**
     * 基于全文的查询
     *
     * @param indexName
     * @return
     */
    public AjaxResult<JSONArray> matchSearch(String indexName) {
        /**
         * post indexName/_search
         * {
         *     "query":{
         *         "multi_match":{
         *             "query": "Apache",
         *             "fields": ["name","corp"]
         *         }
         *     }
         * }
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("Apache", "name", "corp"));
        return send(indexName, searchSourceBuilder);
    }

    /**
     * 模糊查询
     *
     * @return
     */
    public AjaxResult<JSONArray> fuzzySearch(String indexName) {
        /**
         * get /indexName/_search
         * {
         *     "query":{
         *         "fuzzy":{
         *             "name":{
         *                 "value":"Apache",
         *                 "fuzziness":"1"
         *             }
         *         }
         *     }
         * }
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "Apache");
        fuzzyQueryBuilder.fuzziness(Fuzziness.ONE);
        searchSourceBuilder.query(fuzzyQueryBuilder);
        return send(indexName, searchSourceBuilder);
    }

    /**
     * 组合查询
     *
     * @param indexName
     * @return
     */
    public AjaxResult<JSONArray> boolMatch(String indexName) {
        /**
         * post indexName/_search
         * {
         *     "query":{
         *         "bool":{
         *             "must":[
         *                  {"match":{"name":"Apache"}}
         *             ],
         *             "should":[
         *                  {"range":{
         *                      "stars":{
         *                          "gte":200,
         *                          "lte":1000
         *                      }
         *                  }}
         *             ]
         *         }
         *     }
         * }
         */
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "Apache"))
                .should(QueryBuilders.rangeQuery("stars").gte(200).lte(1000));
        searchSourceBuilder.query(boolQueryBuilder);
        return send(indexName, searchSourceBuilder);
    }

}
