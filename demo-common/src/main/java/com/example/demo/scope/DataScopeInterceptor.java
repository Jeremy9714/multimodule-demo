package com.example.demo.scope;

import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.example.demo.utils.CollectionUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 15:34
 * @Version: 1.0
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class DataScopeInterceptor implements Interceptor {
    public DataScopeInterceptor() {
    }

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        } else {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            String originalSql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            DataScope dataScope = this.findDataScopeObject(parameterObject);
            if (dataScope == null) {
                return invocation.proceed();
            } else {
                String scopeName = dataScope.getScopeName();
                List<String> deptIds = dataScope.getDeptIds();
                String join = CollectionUtils.join(deptIds, ",");
                originalSql = "select * from (" + originalSql + ") temp_data_scope where temp_data_scope." + scopeName + " in (" + join + ")";
                metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
                return invocation.proceed();
            }
        }
    }

    public DataScope findDataScopeObject(Object parameterObj) {
        if (parameterObj instanceof DataScope) {
            return (DataScope) parameterObj;
        } else {
            if (parameterObj instanceof Map) {
                Iterator var2 = ((Map) parameterObj).values().iterator();

                while (var2.hasNext()) {
                    Object val = var2.next();
                    if (val instanceof DataScope) {
                        return (DataScope) val;
                    }
                }
            }

            return null;
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }
}
