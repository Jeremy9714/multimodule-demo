package com.example.demo.util.multidatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/14 15:09
 * @Version: 1.0
 */
public enum DataFormatEnum {

    /* 数字类型处理 */
    INT("int", "int", "number", "int", "numeric", "integer", "int4", "int"),
    INTEGER("integer", "int", "number", "integer", "numeric", "integer", "int4", "int"),
    DOUBLE("double", "double", "number", "numeric", "numeric", "double", "int4", "double"),
    FLOAT("float", "float", "float", "float", "numeric", "float", "float", "float"),
    NUMERIC("numeric", "float", "float", "decimal", "numeric", "float", "float", "decimal"),

    /* 日期类型处理 */
    DATE("date", "date", "date", "datetime", "date", "date", "date", "date"),
    DATETIME("datetime", "datetime", "date", "datetime", "timestamp", "date", "date", "date"),
    TIMESTAMP("timestamp", "timestamp", "date", "datetime", "timestamp", "timestamp", "date", "timestamp"),

    /* 二进制类型处理 */
    BLOB("blob", "blob", "blob", "binary", "bytea", "blob", "blob", "string"),

    /* 字符串类型处理 */
    CHAR("char", "char", "char", "char", "char", "char", "char", "char"),
    VARCHAR("varchar", "varchar", "varchar2", "nvarchar", "varchar", "varchar", "varchar", "varchar");

    private String format;

    private String mysqlFormat;
    private String oracleFormat;
    private String sqlserverFormat;
    private String postgresqlFormat;
    private String phbaseFormat;
    private String kingbaseV8Format;
    private String hive2Format;
    private static final Logger logger = LoggerFactory.getLogger(DataFormatEnum.class);

    DataFormatEnum(String format, String mysqlFormat, String oracleFormat, String sqlserverFormat, String postgresqlFormat, String phbaseFormat, String kingbaseV8Format, String hive2Format) {
        this.format = format;
        this.mysqlFormat = mysqlFormat;
        this.oracleFormat = oracleFormat;
        this.sqlserverFormat = sqlserverFormat;
        this.postgresqlFormat = postgresqlFormat;
        this.phbaseFormat = phbaseFormat;
        this.kingbaseV8Format = kingbaseV8Format;
        this.hive2Format = hive2Format;
    }

    public String getFormat() {
        return format;
    }

    public String getMysqlFormat() {
        return mysqlFormat;
    }

    public String getOracleFormat() {
        return oracleFormat;
    }

    public String getSqlserverFormat() {
        return sqlserverFormat;
    }

    public String getPhbaseFormat() {
        return phbaseFormat;
    }

    public String getPostgresqlFormat() {
        return postgresqlFormat;
    }

    public String getKingbaseV8Format() {
        return kingbaseV8Format;
    }

    public String getHive2Format() {
        return hive2Format;
    }

    /**
     * 对外提供的方法
     *
     * @param rawDbType 原始数据源类型:mysql、oracle、sqlserver、postgresql、phbase
     * @param dbType    目标数据库类型:mysql、oracle、sqlserver、postgresql、phbase
     * @param format    待转换的初始格式
     * @return 返回初始格式在指定数据库中的对应格式
     */
    public static String getTargetFormat(String rawDbType, String dbType, String format) {
        String targetFormat = "";
        DataFormatEnum dataFormat = getByStringTypeCode(DataFormatEnum.class, rawDbType, format);
        if (dataFormat == null) {
            // 如果为获取到对应的格式，默认设置为可变字符串
            dataFormat = DataFormatEnum.VARCHAR;
        }
        switch (dbType) {
            case "mysql":
                targetFormat = dataFormat.getMysqlFormat();
                break;
            case "oracle":
                targetFormat = dataFormat.getOracleFormat();
                break;
            case "sqlserver":
                targetFormat = dataFormat.getSqlserverFormat();
                break;
            case "postgresql":
                targetFormat = dataFormat.getPostgresqlFormat();
                break;
            case "phbase":
                targetFormat = dataFormat.getPhbaseFormat();
                break;
            case "kingbaseV8":
                targetFormat = dataFormat.getKingbaseV8Format();
                break;
            case "hive1":
                targetFormat = dataFormat.getHive2Format();
                break;
            default:
                logger.error("{}尚未适配", dbType);
                break;
        }
        return targetFormat;
    }

    private static <T extends Enum<T>> T getByStringTypeCode(Class<T> clazz, String rawDbType, String format) {
        T result = null;
        try {
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod("get" + rawDbType.substring(0, 1).toUpperCase() + rawDbType.substring(1) + "Format");
            String typeCodeValue = null;
            for (T entity : arr) {
                typeCodeValue = targetMethod.invoke(entity).toString();
                if (typeCodeValue.equals(format)) {
                    result = entity;
                    break;
                }
            }
        } catch (RuntimeException re) {
            logger.error("动态获取并生成枚举实例失败", re);
        } catch (Exception e) {
            logger.error("动态获取并生成枚举实例失败", e);
        }
        return result;
    }
}
