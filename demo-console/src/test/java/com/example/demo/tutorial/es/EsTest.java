package com.example.demo.tutorial.es;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.console.Application;
import com.example.demo.tutorial.es.entity.Student;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 10:09
 * @Version: 1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class EsTest {

    private final String INDEX_NAME = "student-index";

    @Autowired
    @Qualifier("highLevelClient")
    private RestHighLevelClient client;

    @Test
    public void createIndex() {
        boolean acknowledged = false;
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("properties").startObject()
                    .field("name").startObject().field("type", "keyword").endObject()
                    .field("age").startObject().field("type", "integer").endObject()
                    .field("birthday").startObject().field("type", "date").field("format", "yyyy-MM-dd").endObject()
                    .endObject()
                    .endObject();
            createIndexRequest.mapping("_doc", contentBuilder);
            CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            acknowledged = response.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("索引创建结果: " + acknowledged);
    }

    @Test
    public void deleteIndex() {
        boolean acknowledged = false;
        try {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_NAME);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = response.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("索引删除结果: " + acknowledged);
    }

    @Test
    public void insertDoc() {
        Student student = new Student();
        student.setName("Tom").setAge(18).setBirthday("1997-02-26");
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME, "_doc");
        try {
            indexRequest.id("1");
            indexRequest.timeout(TimeValue.timeValueSeconds(5));
            indexRequest.source(JSON.toJSONString(student), XContentType.JSON);
            IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
            if (ObjectUtil.isNotNull(response)) {
                String docId = response.getId();
                DocWriteResponse.Result result = response.getResult();
                if (ObjectUtil.equals(DocWriteResponse.Result.CREATED, result)) {
                    System.out.println("新增文档成功：" + docId);
                } else {
                    System.out.println("更新文档成功：" + docId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertBulkDoc() {
        List<Student> students = getData();
        BulkRequest bulkRequest = new BulkRequest();
        try {
            bulkRequest.timeout("30s");
            AtomicInteger atomicInteger = new AtomicInteger(1);
            students.forEach(student -> bulkRequest.add(new IndexRequest(INDEX_NAME, "_doc", String.valueOf(atomicInteger.getAndIncrement()))
                    .source(JSON.toJSONString(student), XContentType.JSON)));
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            System.out.println("批量插入结果: " + response.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDoc() {
        GetRequest getRequest = new GetRequest(INDEX_NAME);
        try {
            getRequest.type("_doc");
            getRequest.id("7");
//            getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
            boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
            if (exists) {

                GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
                String source = getResponse.getSourceAsString();
                String index = getResponse.getIndex();
                String id = getResponse.getId();
                System.out.println("查询结果: " + source);

                XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                        .startObject()
                        .field("birthday", "2000-01-01")
                        .endObject();
                UpdateRequest updateRequest = new UpdateRequest(index, "_doc", id);
                updateRequest.doc(contentBuilder);
                updateRequest.fetchSource(true);
                updateRequest.docAsUpsert(true);
                UpdateResponse response = client.update(updateRequest, RequestOptions.DEFAULT);
                GetResult getResult = response.getGetResult();
                System.out.println("查询结果: " + getResult.sourceAsString());
            } else {
                System.out.println("文档不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteDoc() {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX_NAME, "_doc", "8");
        try {
            deleteRequest.timeout("3s");
            DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
            if (ObjectUtil.isNotNull(response)) {
                DocWriteResponse.Result result = response.getResult();
                if (ObjectUtil.equals(DocWriteResponse.Result.DELETED, result)) {
                    System.out.println("文档删除成功");
                } else {
                    System.out.println("文档不存在");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchTest1() {
        /**
         * GET index_name/_search
         * {
         *     "query": {
         *         "match_all": {}
         *     },
         *     "_source":{
         *         "_includes":[
         *          "name","birthday"
         *         ]
         *     }
         * }
         */
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.matchAllQuery());
            builder.sort(new FieldSortBuilder("age").order(SortOrder.ASC));
            builder.fetchSource(new String[]{"name", "birthday"}, null);
            JSONArray resultArr = send(INDEX_NAME, builder);
            System.out.println("查询结果: ");
            resultArr.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchTest2() {
        /**
         * GET index-name/_search
         * {
         *     "query":{
         *         "term":{
         *             "name":"Jeremy"
         *         }
         *     }
         * }
         */
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.termQuery("name", "Jeremy"));
            builder.from(0);
            builder.size(10);
            JSONArray resultArr = send(INDEX_NAME, builder);
            resultArr.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aggSearchTest1() {
        /**
         * GET index-name/_search
         * {
         *     "query":{
         *         "terms":{
         *             "name": ["Jeremy", "Jason", "Jack"]
         *         }
         *     },
         *     "aggs":{
         *         "birthday_histo":{
         *             "date_histogram":{
         *                 "field":"birthday",
         *                 "interval":"month",
         *                 "format":"yyyy-MM-dd"
         *             }
         *         },
         *     },
         *     "from":0,
         *     "size":10
         * }
         */
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.from(0);
            builder.size(10);
            builder.query(QueryBuilders.termsQuery("name", "Jeremy", "Jack", "Jason"));
            builder.aggregation(AggregationBuilders.dateHistogram("birthday_histo")
                    .field("birthday")
                    .dateHistogramInterval(DateHistogramInterval.MONTH)
                    .format("yyyy-MM-dd")
            );
            JSONArray resultArr = sendAggregation(INDEX_NAME, builder);
            resultArr.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aggSearchTest2() {
        /**
         * GET index-name/_search
         * {
         *     "query":{
         *         "terms":{
         *             "name":[
         *              "Jeremy","Jack","Mike","Jason"
         *             ]
         *         }
         *     },
         *     "aggs":{
         *         "birthday_histo":{
         *             "date_histogram":{
         *                 "fields":"birthday",
         *                 "interval":"year",
         *                 "format":"yyyy-MM-dd"
         *             },
         *             "aggs":{
         *                 "avg_age":{
         *                     "field":"age"
         *                 }
         *             }
         *         }
         *     }
         * }
         */
        try {
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.termsQuery("name", "Jeremy", "Jack", "Jason", "Mike"));
            builder.aggregation(AggregationBuilders.dateHistogram("birthday_histo")
                    .field("birthday")
                    .dateHistogramInterval(DateHistogramInterval.YEAR)
                    .format("yyyy-MM-dd")
                    .subAggregation(AggregationBuilders.avg("avg_age").field("age")));
            JSONArray resultArr = sendAggregation(INDEX_NAME, builder);
            resultArr.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @param index
     * @param builder
     * @return
     * @throws IOException
     */
    private JSONArray send(String index, SearchSourceBuilder builder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println("查询结果条数: " + hits.totalHits);
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSONObject.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 聚合查询
     *
     * @param index
     * @param builder
     * @return
     * @throws IOException
     */
    private JSONArray sendAggregation(String index, SearchSourceBuilder builder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        searchRequest.source(builder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        System.out.println("查询结果条数: " + hits.totalHits);
        JSONArray jsonArray = new JSONArray();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = JSONObject.parseObject(sourceAsString);
            jsonArray.add(jsonObject);
        }
        // 处理aggregations
        Aggregations aggregations = response.getAggregations();
        for (Aggregation aggregation : aggregations) {
            String jsonString = JSON.toJSONString(aggregation);
            jsonArray.add(JSONObject.parseObject(jsonString));
            // 处理桶聚集
            List<? extends Histogram.Bucket> buckets = ((Histogram) aggregation).getBuckets();
            for (Histogram.Bucket bucket : buckets) {
                String key = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();
                ParsedAvg avgAge = (ParsedAvg) bucket.getAggregations().getAsMap().get("avg_age");
                System.out.println("---------------------------------");
                if (ObjectUtil.isNotNull(avgAge)) {
                    String avgAgeValue = avgAge.getValueAsString();
                    System.out.println("key: [" + key + "]\t docCount: [" + docCount + "]\t avg_age: [" + avgAgeValue + "]");
                } else {
                    System.out.println("key: [" + key + "]\t docCount: [" + docCount + "]");
                }

            }
        }
        return jsonArray;
    }

    private List<Student> getData() {
        List<Student> students = new ArrayList<>();
        Student sean = new Student().setName("Sean").setAge(20).setBirthday("1998-02-14");
        Student jeremy = new Student().setName("Jeremy").setAge(21).setBirthday("1993-03-01");
        Student jason = new Student().setName("Jason").setAge(23).setBirthday("1993-02-19");
        Student jack = new Student().setName("Jack").setAge(17).setBirthday("1997-07-31");
        Student thomas = new Student().setName("Thomas").setAge(24).setBirthday("2000-09-28");
        Student lilly = new Student().setName("Lilly").setAge(31).setBirthday("1999-01-31");
        Student mike = new Student().setName("Mike").setAge(19).setBirthday("1998-10-01");
        students.add(sean);
        students.add(jeremy);
        students.add(jack);
        students.add(jason);
        students.add(thomas);
        students.add(lilly);
        students.add(mike);
        return students;
    }
}
