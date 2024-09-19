package com.example.demo.util.multidatabase;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @Description: Database方法接口
 * @Author: zhangchy05 on 2024/9/14 13:46
 * @Version: 1.0
 */
public interface IDatabase {

    /**
     * 是否可以连接
     *
     * @return
     */
    boolean canConnect();

    /**
     * 关闭数据连接
     *
     * @return
     */
    boolean closeDatasource();

    /**
     * 是否可以连接 0连接不通 1可以连接
     *
     * @return
     */
    JSONObject isDatabaseConnected();

    /**
     * 执行sql语句
     *
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 执行sql语句
     *
     * @param sql
     * @param params
     */
    void executeSql(String sql, List<String> params);

    /**
     * 拼接创建表sql语句
     *
     * @param tableName  表名
     * @param columnList 表字段集合
     */
    String createTableSql(String tableName, List<Map<String, Object>> columnList);

    /**
     * 批量插入数据
     *
     * @param tableName  表名
     * @param columnList 字段名李彪
     * @param dataList   数据列表
     */
    void batchInsert(String tableName, List<Map<String, Object>> columnList, List<Map<String, Object>> dataList);

    /**
     * 批量插入数据
     *
     * @param tableName  表名
     * @param pk         主键
     * @param columnList 字段名列表
     * @param dataList   数据列表
     */
    void batchInsert(String tableName, String pk, List<Map<String, Object>> columnList, List<Map<String, Object>> dataList);

    /**
     * 获取数据库连接
     *
     * @return
     */
    Connection getConnection();

    String getDbName();

    /**
     * 获取jdbcurl
     *
     * @return
     */
    String getJdbcUrl();

    /**
     * 获取数据库所有的schema
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    List querySchema();

    /**
     * 判断表是否存在
     *
     * @param tableName
     * @return
     */
    boolean isTableExists(String tableName);

    /**
     * 获取此数据库支持的所有数据类型的描述
     *
     * @return 数据类型列表
     */
    @SuppressWarnings("rawtypes")
    List queryAllDataType();

    /**
     * 查询单条数据
     *
     * @param sql
     * @return
     */
    @SuppressWarnings("rawtypes")
    Map queryData(String sql);

    /**
     * 查询多条数据
     *
     * @param sql
     * @return
     */
    List queryDataList(String sql);

    /**
     * 分页查询数据
     *
     * @param sql
     * @param currentPage
     * @param pageSize
     * @param params
     * @return
     */
    @SuppressWarnings("unused")
    Map queryDataByPage(String sql, int currentPage, int pageSize, Map<String, Object> params);

    /**
     * 查询总记录数
     *
     * @param tableName
     * @param params
     * @return
     */
    int queryDataCount(String tableName, Map<String, Object> params);

    /**
     * 查询表的描述
     *
     * @param schema 数据库的schema
     * @param type   "TABLE", "VIEW"
     * @return
     */
    @SuppressWarnings("rawtypes")
    List queryTable(String schema, String type);

    /**
     * 查询数据库下的表
     *
     * @param schema    数据库的schema
     * @param type      "TABLE", "VIEW"
     * @param tableName 表名称
     * @return
     */
    @SuppressWarnings("rawtypes")
    List queryTable(String schema, String type, String tableName);

    /**
     * 获取表的索引和统计信息的描述
     *
     * @param schema
     * @param tableName
     * @return 索引信息列表
     */
    @SuppressWarnings("rawtypes")
    public List queryTableIndex(String schema, String tableName);

    /**
     * 获取表列的描述
     *
     * @param schema    数据库的schema
     * @param tableName 表名称
     * @return 列描述信息列表
     */
    @SuppressWarnings("rawtypes")
    public List queryTableColumns(String schema, String tableName);

    /**
     * 主键列的描述
     *
     * @param schema
     * @param tableName
     * @return 主键列列表
     */
    @SuppressWarnings("rawtypes")
    public List queryTablePrimaryKey(String schema, String tableName);

    /**
     * 外键列的描述
     *
     * @param schema
     * @param tableName
     * @return 外键列列表
     */
    @SuppressWarnings("rawtypes")
    public List queryTableImportKey(String schema, String tableName);

    /**
     * 对外提供的方法
     *
     * @param format 待转换的初始格式
     * @return 返回初始格式在对应数据库中的对应格式
     */
    public String getDataFormat(DataFormatEnum format);

    /**
     * 获得driver
     *
     * @return 数据库的jdbc驱动
     */
    public String getDriverClass();

    /**
     * 释放连接
     *
     * @param conn 数据库连接
     * @param psmt sql执行语句
     * @param rs   结果集
     */
    public void closeObject(Connection conn, PreparedStatement psmt, ResultSet rs);

    /**
     * 创建表
     *
     * @param tableName
     * @param columns
     */
    public void createTable(String tableName, String tableDesc, JSONArray columns, JSONObject extendParam);

    public Long getTableDataNum(String tableName);

    //String getSchema();

    /**
     * 获取当前连接数据库类型
     *
     * @return
     */
    public String getDbType();

    /**
     * 同步表结构
     *
     * @param rawDbType
     * @param tableName
     * @param columns
     * @param extendParam
     */
    public void syncTableStruct(String rawDbType, String tableName, JSONArray columns, JSONObject extendParam);

    public Map queryDataByPage(String tableName, int currentPage, int pageSize, List<Map<String, String>> params);

    public String handleColumnName(String columnName);

    /**
     * 查询多条数据
     *
     * @param sql
     * @return 数据列表
     */
    public List<List<Object>> queryDataListWithHeader(String sql);

    boolean updateDataList(String sql, List<Map<String, Object>> dataList);

    DruidDataSource getDruidDataSource();

}
