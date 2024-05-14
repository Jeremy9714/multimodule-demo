package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:28
 * @Version: 1.0
 */
public class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);
    private static final MessageFormat formatLimitString = new MessageFormat("SELECT GLOBAL_TABLE.* FROM ( SELECT ROW_NUMBER() OVER( {0}) AS __MYSEQ__,TEMP_TABLE.* FROM  ( {1} ) TEMP_TABLE) GLOBAL_TABLE WHERE GLOBAL_TABLE.__MYSEQ__>{2}");

    public DbUtils() {
    }

    public static JSONObject getDbPaginate(Connection con, int pageNumber, int pageSize, String select, String sqlExceptSelect) throws SQLException {
        String dataType = "Oracle";
        StringBuilder sqlBuilder = new StringBuilder(255);
        DatabaseMetaData metaData = con.getMetaData();
        if (metaData != null) {
            String name = metaData.getDatabaseProductName();
            if (StringUtils.isNotBlank(name)) {
                if (name.startsWith("Oracle")) {
                    dataType = "Oracle";
                    forOraclePaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("MySQL")) {
                    dataType = "MySQL";
                    forMySQLPaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("DB2/")) {
                    dataType = "DB2";
                    forDB2Paginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("DM")) {
                    dataType = "Dameng";
                    forDmPaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("Adaptive")) {
                    dataType = "Sybase";
                    forSybasePaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("Microsoft")) {
                    String ver = metaData.getDatabaseProductVersion();
                    ver = ver.substring(0, ver.indexOf("."));
                    if (StringUtils.isNotBlank(ver)) {
                        Double dInt = Double.valueOf(ver);
                        if (dInt >= 9.0D) {
                            dataType = "MS2005-SQL";
                            forSQLServer2005Paginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                        } else {
                            dataType = "MS-SQL";
                            forSQLServer2000Paginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                        }
                    } else {
                        dataType = "MS-SQL";
                        forSQLServer2005Paginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                    }
                } else if (name.startsWith("KingbaseES")) {
                    dataType = "KingbaseES";
                    forKingbasePaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                } else if (name.startsWith("EnterpriseDB")) {
                    dataType = "EnterpriseDB";
                    forEnterpriseDbPaginate(sqlBuilder, pageNumber, pageSize, select, sqlExceptSelect);
                }
            } else {
                logger.warn("查找不到数据类型.");
            }
        }

        JSONObject json = new JSONObject();
        json.put("type", dataType);
        json.put("sql", sqlBuilder.toString());
        return json;
    }

    public static void forSQLServer2005Paginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        String paramString = select + " " + sqlExceptSelect;
        int start = (pageNumber - 1) * pageSize;
        String sqls = "select top ___TOP_NUM___ __TEMP_ORDER_BY_COLUMN__=0, " + paramString.substring(6);
        String sqlserver = formatLimitString.format(new String[]{"ORDER BY __TEMP_ORDER_BY_COLUMN__", sqls, start + ""});
        sqlserver = StringUtils.replace(sqlserver, "___TOP_NUM___", pageSize + start + "");
        sql.append(sqlserver);
    }

    public static void forSQLServer2000Paginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
    }

    public static void forSybasePaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
    }

    public static void forKingbasePaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        String paramString = select + " " + sqlExceptSelect;
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        StringBuffer localStringBuffer = new StringBuffer(100);
        localStringBuffer.append(paramString);
        localStringBuffer.append(" limit ").append(start).append(" offset ").append(end);
        sql.append(localStringBuffer);
    }

    public static void forEnterpriseDbPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        String paramString = select + " " + sqlExceptSelect;
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        StringBuffer localStringBuffer = new StringBuffer(100);
        localStringBuffer.append("SELECT * FROM ( SELECT ROW_.*, ROWNUM ROWNUM_ FROM ( ");
        localStringBuffer.append(paramString);
        localStringBuffer.append(" ) ROW_ WHERE ROWNUM <= ").append(start).append(") WHERE ROWNUM_ > ").append(end);
        sql.append(localStringBuffer);
    }

    public static void forDmPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        sql.append("select top ").append(start).append(",").append(end).append("from (");
        sql.append(select).append(" ").append(sqlExceptSelect);
        sql.append(")");
    }

    public static void forDB2Paginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        String paramString = select + " " + sqlExceptSelect;
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        sql.append("SELECT * FROM  (SELECT B.*, ROWNUMBER() OVER() AS RN FROM  (SELECT * FROM (").append(paramString).append(")   ) AS B   )AS A ");
        sql.append("WHERE A.RN BETWEEN ");
        sql.append(start).append(" AND ").append(end);
    }

    public static void forMySQLPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        int offset = pageSize * (pageNumber - 1);
        sql.append(select).append(" ");
        sql.append(sqlExceptSelect);
        sql.append(" limit ").append(offset).append(", ").append(pageSize);
    }

    public static void forOracle11gPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        sql.append("select * from (select t.*,rownum as rowno from (");
        sql.append(select).append(" ").append(sqlExceptSelect);
        sql.append("  t) ");
        sql.append(" where rowno between ").append(start).append(" and ").append(end);
    }

    public static void forOraclePaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        sql.append("select * from ( select row_.*, rownum rownum_ from (  ");
        sql.append(select).append(" ").append(sqlExceptSelect);
        sql.append(" ) row_ where rownum <= ").append(end).append(") table_alias");
        sql.append(" where table_alias.rownum_ >= ").append(start);
    }

    public static void forOracle12gPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        int start = (pageNumber - 1) * pageSize + 1;
        int end = pageNumber * pageSize;
        sql.append("select * from ( select s.*,row_number() over(ORDER BY rownum) ID_TONGJI_INDEX, COUNT(1) over() ID_TONGJI_ALL from  (");
        sql.append(select).append(" ").append(sqlExceptSelect);
        sql.append(") s ) where ID_TONGJI_INDEX between ");
        sql.append(start).append(" and ").append(end);
    }

    public static String replaceFormatSqlOrderBy(String sql) {
        sql = sql.replaceAll("(\\s)+", " ");
        int index = sql.toLowerCase().lastIndexOf("order by");
        if (index > sql.toLowerCase().lastIndexOf(")")) {
            String sql1 = sql.substring(0, index);
            String sql2 = sql.substring(index);
            sql2 = sql2.replaceAll("[oO][rR][dD][eE][rR] [bB][yY] [一-龥a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[一-龥a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*", "");
            return sql1 + sql2;
        } else {
            return sql;
        }
    }

    public String getPageSQL(String sql, int start, int length) {
        StringBuffer pagingSelect = new StringBuffer(100);
        pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        pagingSelect.append(sql);
        pagingSelect.append(" ) row_ where rownum <= " + (start + length) + ") where rownum_ > " + start);
        return pagingSelect.toString();
    }

    public String getPageSQLBySize(String sql, int currentPage, int pageSize) {
        StringBuffer pagingSelect = new StringBuffer(100);
        pagingSelect.append("select * from ( select row_.*, rownum  rownum_ from ( ");
        pagingSelect.append(sql);
        pagingSelect.append(" ) row_ where rownum <= " + currentPage * pageSize + ") where rownum_ > " + (currentPage - 1) * pageSize);
        return pagingSelect.toString();
    }

    public static String buildPaginationSql(Connection con, String originalSql, int offset, int limit) {
        try {
            String jdbcUrl = con.getMetaData().getURL();
            if (jdbcUrl.startsWith("jdbc:informix-sqli:")) {
                return buildGbasePaginationSql(originalSql, offset, limit);
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    public static String buildGbasePaginationSql(String originalSql, int offset, int limit) {
        StringBuilder sql = new StringBuilder(originalSql);
        sql.append(" skip ").append(offset).append(" first ").append(limit);
        return sql.toString();
    }
}
