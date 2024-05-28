package com.example.demo.tutorial.es

import io.searchbox.core.Count
import io.searchbox.core.CountResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/28 15:57
 * @Version: 1.0
 */
@Service
class StudentSearchService extends AbstractSearchService {
    
    private static final Logger log = LoggerFactory.getLogger(StudentSearchService.class);
    private static final String INDEX_NAME = "student-index";

    BigInteger getStudentSortedByNameAndAge(String name) {
        def query = """
            {
                "query":{
                    "term":{
                        "name": "${name}"
                    }
                }
            }
        """
        Count count = new Count.Builder().query(query).addIndex(INDEX_NAME).build()
        CountResult countResult = execute(count)
        if (countResult.isSucceeded()) {
            Double doubleCount = countResult.getCount()
            return doubleCount
        } else {
            log.error("查询失败: {}", countResult)
        }
        return null
    }
}
