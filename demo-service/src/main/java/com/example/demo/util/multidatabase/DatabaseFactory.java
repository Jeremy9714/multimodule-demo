package com.example.demo.util.multidatabase;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 获取数据库处理handler的工厂类
 * @Author: zhangchy05 on 2024/9/14 15:27
 * @Version: 1.0
 */
public class DatabaseFactory {
    private static Logger logger = LoggerFactory.getLogger(DatabaseFactory.class);

    public static IDatabase getDatabase(String dbType, String databaseName, String host, Integer port, String username, String password, String jdbcType, JSONObject extInfo) {
        if (StringUtils.isBlank(dbType)) {
            logger.error("dbType为空！");
            return null;
        }

        IDatabase databaseHandler = null;
        // TODO 初始化handler
        
        return databaseHandler;
    }

}
