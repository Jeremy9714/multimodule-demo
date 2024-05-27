package com.example.demo.tutorial.es.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.constant.es.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/24 14:44
 * @Version: 1.0
 */
@Service
@Slf4j
public class AggsSearchService {

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
            // TODO 聚合
            Aggregations aggregations = searchResponse.getAggregations();
            for (Aggregation aggregation : aggregations) {
                String jsonString = JSON.toJSONString(aggregation);
                jsonArray.add(JSONObject.parseObject(jsonString));

                //这里可以拿到具体的桶聚集，做特殊处理
                List<? extends Histogram.Bucket> buckets = ((Histogram) aggregation).getBuckets();
                for (Histogram.Bucket bucket : buckets) {
                    System.out.println("--------------------------------------");
                    System.out.println(bucket.getKeyAsString());
                    System.out.println(bucket.getDocCount());
                    ParsedAvg parsedAvg = (ParsedAvg) bucket.getAggregations().getAsMap().get("avg_price");
                    System.out.println(parsedAvg.getValueAsString());
                }
            }

            return AjaxResult.success(jsonArray);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return AjaxResult.fail(null);
    }

    /**
     * 聚集查询
     */
    public AjaxResult<JSONArray> aggsSearch(String indexName) {
        /**
         * high level client不能使用过滤条件filter_path，如果要使用，只能使用low level client
         * POST /fisher/_search?filter_path=aggregations
         * {
         * 	"query": {
         * 		"term": {"name": "Apache"}
         *        },
         * 	"aggs":
         *    {
         * 		"date_price_histogram": {
         * 			"date_histogram": {
         * 				"field": "timestamp",
         * 				"fixed_interval": "30d"
         *            },
         * 			"aggs": {
         * 				"avg_stars": {"avg": {"field": "stars"}}
         *            }
         *        }
         *    }
         * }
         */
        // 拼接query部分
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "Apache"));

//        //拼接聚集部分
//        DateHistogramAggregationBuilder histogramBuilder
//                = AggregationBuilders.dateHistogram("date_price_histogram");
//        histogramBuilder.field("timestamp").fixedInterval(DateHistogramInterval.days(30));
//        //嵌套聚集部分
//        histogramBuilder.subAggregation(AggregationBuilders.avg("avg_stars").field("stars"));
//        searchSourceBuilder.aggregation(date_price_histogram);

        return send(indexName, searchSourceBuilder);
    }

}
