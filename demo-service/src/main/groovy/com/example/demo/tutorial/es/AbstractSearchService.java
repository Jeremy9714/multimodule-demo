package com.example.demo.tutorial.es;

import com.google.gson.Gson;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 15:52
 * @Version: 1.0
 */
@Slf4j
public class AbstractSearchService {

    @Autowired
    private JestClient client;

    @Autowired
    private Gson gson;

    public JestResult execute(Action action) {
        try {
            JestResult result = client.execute(action);
            if (!result.isSucceeded()) {
                log.warn("Failed to execute elasticsearch action {} {}", result.getErrorMessage(), result.getJsonString());
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
