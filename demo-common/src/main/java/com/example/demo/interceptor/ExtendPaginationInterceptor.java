package com.example.demo.interceptor;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.plugins.parser.ISqlParser;
import com.baomidou.mybatisplus.plugins.parser.SqlInfo;
import com.baomidou.mybatisplus.toolkit.JdbcUtils;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.example.demo.utils.DbUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:25
 * @Version: 1.0
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class ExtendPaginationInterceptor extends PaginationInterceptor {
    private boolean localPage = false;
    private ISqlParser sqlParser;
    private boolean overflowCurrent = false;
    private String dialectType;
    private String dialectClazz;

    public ExtendPaginationInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        } else {
            RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
            if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                if (!this.localPage) {
                    return invocation.proceed();
                }

                rowBounds = PageHelper.getPagination();
                if (rowBounds == null) {
                    return invocation.proceed();
                }
            }

            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            Connection connection = (Connection) invocation.getArgs()[0];
            DBType dbType = StringUtils.isNotEmpty(this.dialectType) ? DBType.getDBType(this.dialectType) : JdbcUtils.getDbType(connection.getMetaData().getURL());
            if (rowBounds instanceof Pagination) {
                Pagination page = (Pagination) rowBounds;
                boolean orderBy = true;
                if (page.isSearchCount()) {
                    SqlInfo sqlInfo = SqlUtils.getCountOptimize(this.sqlParser, originalSql);
                    orderBy = sqlInfo.isOrderBy();
                    this.queryTotal(this.overflowCurrent, sqlInfo.getSql(), mappedStatement, boundSql, page, connection);
                    if (page.getTotal() <= 0) {
                        return invocation.proceed();
                    }
                }

                String buildSql = SqlUtils.concatOrderBy(originalSql, page, orderBy);
                if (DBType.OTHER.getDb().equals(dbType.getDb())) {
                    originalSql = DbUtils.buildPaginationSql(connection, buildSql, page.getOffsetCurrent(), page.getSize());
                } else {
                    originalSql = DialectFactory.buildPaginationSql(page, buildSql, dbType, this.dialectClazz);
                }
            } else if (DBType.OTHER.getDb().equals(dbType.getDb())) {
                originalSql = DbUtils.buildPaginationSql(connection, originalSql, ((RowBounds) rowBounds).getOffset(), ((RowBounds) rowBounds).getLimit());
            } else {
                originalSql = DialectFactory.buildPaginationSql((RowBounds) rowBounds, originalSql, dbType, this.dialectClazz);
            }

            metaObject.setValue("delegate.boundSql.sql", originalSql);
            metaObject.setValue("delegate.rowBounds.offset", 0);
            metaObject.setValue("delegate.rowBounds.limit", 2147483647);
            return invocation.proceed();
        }
    }

    public ExtendPaginationInterceptor setLocalPage(boolean localPage) {
        this.localPage = localPage;
        return this;
    }

    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }

        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }

    }

    public ExtendPaginationInterceptor setDialectType(String dialectType) {
        this.dialectType = dialectType;
        return this;
    }

    public ExtendPaginationInterceptor setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
        return this;
    }

    public ExtendPaginationInterceptor setOverflowCurrent(boolean overflowCurrent) {
        this.overflowCurrent = overflowCurrent;
        return this;
    }

    public ExtendPaginationInterceptor setSqlParser(ISqlParser sqlParser) {
        this.sqlParser = sqlParser;
        return this;
    }
}
