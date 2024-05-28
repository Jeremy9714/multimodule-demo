package com.example.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 15:38
 * @Version: 1.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "global.jest", value = "enable", havingValue = "1")
public class JestConfig {

    @Value("${spring.elasticsearch.cluster.nodes:}")
    private String clusterNodes;

    @Value("${spring.elasticsearch.username:}")
    private String username;

    @Value("${spring.elasticsearch.password:}")
    private String password;

    @Bean
    public JestClient jestClient() {
        log.info("**** elasticsearch cluster nodes: {}", clusterNodes);
        String[] nodes = clusterNodes.split(",");
        List<String> nodeList = Arrays.asList(nodes);
        HttpClientConfig httpClientConfig = null;
        if (StringUtils.isNotBlank(username)) {
            httpClientConfig = new HttpClientConfig.Builder(nodeList)
                    .defaultCredentials(username, password)
                    .multiThreaded(true)
                    .gson(gson())
                    .build();
        } else {
            httpClientConfig = new HttpClientConfig.Builder(nodeList)
                    .multiThreaded(true)
                    .gson(gson())
                    .build();
        }

        JestClientFactory jestClientFactory = new JestClientFactory();
        jestClientFactory.setHttpClientConfig(httpClientConfig);
        return jestClientFactory.getObject();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();
    }
}
