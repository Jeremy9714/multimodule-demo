package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/23 15:46
 * @Version: 1.0
 */
@Configuration
@ConditionalOnProperty(prefix = "global.elasticsearch", value = "enable", havingValue = "1")
public class ESConfig {

    /**
     * 集群或单节点
     */
    @Value("${spring.elasticsearch.nodes:localhost:9200}")
    private String clusterNodes;

    @Bean
    public RestHighLevelClient highLevelClient() {
        String[] nodes = clusterNodes.split(",");
        HttpHost[] httpHostArr = new HttpHost[nodes.length];
        for (int i = 0; i < httpHostArr.length; ++i) {
            String node = nodes[i];
            httpHostArr[i] = new HttpHost(node.split(":")[0], Integer.valueOf(node.split(":")[1]), "http");
        }
        return new RestHighLevelClient(RestClient.builder(httpHostArr));
    }
}
