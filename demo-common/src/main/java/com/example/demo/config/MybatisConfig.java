package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusProperties;
import com.baomidou.mybatisplus.spring.boot.starter.SpringBootVFS;
import com.example.demo.interceptor.ExtendPaginationInterceptor;
import com.example.demo.scope.DataScopeInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 14:45
 * @Version: 1.0
 */
@Configuration
@EnableTransactionManagement(
        order = 2
)
@ConditionalOnProperty(prefix = "global.multi-datasource", value = "enable", havingValue = "0")
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = {"com.example.demo.**.dao", "mapper"})
public class MybatisConfig extends MybatisAutoConfiguration {
    @Autowired
    private MybatisPlusProperties properties;
    @Autowired(required = false)
    private ObjectProvider<Interceptor[]> interceptors;
    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String mysqlKey = "ACCESSIBLE,ADD,ANALYZE,ASC,BEFORE,CASCADE,CHANGE,CONTINUE,DATABASE,DATABASES,DAY_HOUR,DAY_MICROSECOND,DAY_MINUTE,DAY_SECOND,DELAYED,DESC,DISTINCTROW,DIV,DUAL,ELSEIF,ENCLOSED,ESCAPED,EXIT,EXPLAIN,FLOAT4,FLOAT8,FORCE,FULLTEXT,GENERATED,HIGH_PRIORITY,HOUR_MICROSECOND,HOUR_MINUTE,HOUR_SECOND,IF,IGNORE,INDEX,INFILE,INT1,INT2,INT3,INT4,INT8,IO_AFTER_GTIDS,IO_BEFORE_GTIDS,ITERATE,KEY,KEYS,KILL,LEAVE,LIMIT,LINEAR,LINES,LOAD,LOCK,LONG,LONGBLOB,LONGTEXT,LOOP,LOW_PRIORITY,MASTER_BIND,MASTER_SSL_VERIFY_SERVER_CERT,MAXVALUE,MEDIUMBLOB,MEDIUMINT,MEDIUMTEXT,MIDDLEINT,MINUTE_MICROSECOND,MINUTE_SECOND,NO_WRITE_TO_BINLOG,OPTIMIZE,OPTIMIZER_COSTS,OPTION,OPTIONALLY,OUTFILE,PURGE,READ,READ_WRITE,REGEXP,RENAME,REPEAT,REPLACE,REQUIRE,RESIGNAL,RESTRICT,RLIKE,SCHEMA,SCHEMAS,SECOND_MICROSECOND,SEPARATOR,SHOW,SIGNAL,SPATIAL,SQL_BIG_RESULT,SQL_CALC_FOUND_ROWS,SQL_SMALL_RESULT,SSL,STARTING,STORED,STRAIGHT_JOIN,TERMINATED,TINYBLOB,TINYINT,TINYTEXT,UNDO,UNLOCK,UNSIGNED,USAGE,USE,UTC_DATE,UTC_TIME,UTC_TIMESTAMP,VARBINARY,VARCHARACTER,VIRTUAL,WHILE,WRITE,XOR,YEAR_MONTH,ZEROFILL";
    @Value("${spring.datasource.read-size:1}")
    private Integer dataSourceSize;
    @Value("${mybatis-plus.configuration.dbType:other}")
    private String dbType;
    @Autowired
    private DruidDataSourceConfig dataSourceConfig;

    public MybatisConfig() {
    }

    @Bean
    public DataSource dataSource() {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("dataSourceConfig" + this.dataSourceConfig);
        }

        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(this.dataSourceConfig.getDriverClassName());
        ds.setUsername(this.dataSourceConfig.getUsername());
        ds.setPassword(this.dataSourceConfig.getPassword());
        ds.setUrl(this.dataSourceConfig.getUrl());
        ds.setInitialSize(this.dataSourceConfig.getInitialSize());
        ds.setMinIdle(this.dataSourceConfig.getMinIdle());
        ds.setMaxActive(this.dataSourceConfig.getMaxActive());
        ds.setMaxWait((long) this.dataSourceConfig.getMaxWait());
        ds.setValidationQuery(this.dataSourceConfig.getValidationQuery());
        ds.setTestOnBorrow(this.dataSourceConfig.isTestOnBorrow());
        ds.setTestOnReturn(this.dataSourceConfig.isTestOnReturn());
        ds.setTestWhileIdle(this.dataSourceConfig.isTestWhileIdle());
        ds.setTimeBetweenEvictionRunsMillis((long) this.dataSourceConfig.getTimeBetweenEvictionRunsMillis());
        ds.setMaxEvictableIdleTimeMillis((long) this.dataSourceConfig.getMaxEvictableIdleTimeMillis());
        ds.setMinEvictableIdleTimeMillis((long) this.dataSourceConfig.getMinEvictableIdleTimeMillis());
        ds.setPoolPreparedStatements(this.dataSourceConfig.isPoolPreparedStatements());
        ds.setMaxOpenPreparedStatements(this.dataSourceConfig.getMaxOpenPreparedStatements());
        ds.setKeepAlive(true);
        ds.setUseUnfairLock(true);

        try {
            ds.setFilters(this.dataSourceConfig.getFilters());
        } catch (SQLException var3) {
            this.logger.error(var3.getSQLState(), var3);
        }

        return ds;
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(this.dataSource());
        mybatisPlus.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }

        mybatisPlus.setGlobalConfig(this.golableConfig());
        mybatisPlus.setConfiguration(this.properties.getConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins((Interceptor[]) this.interceptors.getIfAvailable());
        }

        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }

        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }

        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        }

        return mybatisPlus;
    }

    @Bean
    public GlobalConfiguration golableConfig() {
        GlobalConfiguration globalConfig = new GlobalConfiguration();
        globalConfig.setDbType(this.dbType);
        globalConfig.setIdType(1);
        globalConfig.setFieldStrategy(2);
        globalConfig.setSqlInjector(new AutoSqlInjector());
        globalConfig.setDbColumnUnderline(true);
        globalConfig.setRefresh(true);
        globalConfig.setCapitalMode(true);
        globalConfig.setSqlKeywords("ACCESSIBLE,ADD,ANALYZE,ASC,BEFORE,CASCADE,CHANGE,CONTINUE,DATABASE,DATABASES,DAY_HOUR,DAY_MICROSECOND,DAY_MINUTE,DAY_SECOND,DELAYED,DESC,DISTINCTROW,DIV,DUAL,ELSEIF,ENCLOSED,ESCAPED,EXIT,EXPLAIN,FLOAT4,FLOAT8,FORCE,FULLTEXT,GENERATED,HIGH_PRIORITY,HOUR_MICROSECOND,HOUR_MINUTE,HOUR_SECOND,IF,IGNORE,INDEX,INFILE,INT1,INT2,INT3,INT4,INT8,IO_AFTER_GTIDS,IO_BEFORE_GTIDS,ITERATE,KEY,KEYS,KILL,LEAVE,LIMIT,LINEAR,LINES,LOAD,LOCK,LONG,LONGBLOB,LONGTEXT,LOOP,LOW_PRIORITY,MASTER_BIND,MASTER_SSL_VERIFY_SERVER_CERT,MAXVALUE,MEDIUMBLOB,MEDIUMINT,MEDIUMTEXT,MIDDLEINT,MINUTE_MICROSECOND,MINUTE_SECOND,NO_WRITE_TO_BINLOG,OPTIMIZE,OPTIMIZER_COSTS,OPTION,OPTIONALLY,OUTFILE,PURGE,READ,READ_WRITE,REGEXP,RENAME,REPEAT,REPLACE,REQUIRE,RESIGNAL,RESTRICT,RLIKE,SCHEMA,SCHEMAS,SECOND_MICROSECOND,SEPARATOR,SHOW,SIGNAL,SPATIAL,SQL_BIG_RESULT,SQL_CALC_FOUND_ROWS,SQL_SMALL_RESULT,SSL,STARTING,STORED,STRAIGHT_JOIN,TERMINATED,TINYBLOB,TINYINT,TINYTEXT,UNDO,UNLOCK,UNSIGNED,USAGE,USE,UTC_DATE,UTC_TIME,UTC_TIMESTAMP,VARBINARY,VARCHARACTER,VIRTUAL,WHILE,WRITE,XOR,YEAR_MONTH,ZEROFILL");
        return globalConfig;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(this.dataSource());
        return template;
    }

    @Bean(
            name = {"transactionManager"}
    )
    public DataSourceTransactionManager transactionManagers() {
        if (this.logger.isDebugEnabled()) {
            this.logger.info("-------------------- transactionManager init ---------------------");
        }

        return new DataSourceTransactionManager(this.dataSource());
    }

    @Bean
    public PaginationInterceptor paginationInterceptor(Properties dspCustomProperties) {
        ExtendPaginationInterceptor paginationInterceptor = new ExtendPaginationInterceptor();
        Object obj = dspCustomProperties.get("pageinterceptor-dialect");
        if (obj != null) {
            String pageInterceptorDialectType = null;
            if (obj instanceof String) {
                pageInterceptorDialectType = (String) obj;
            } else {
                pageInterceptorDialectType = obj.toString();
            }

            if (!pageInterceptorDialectType.isEmpty()) {
                paginationInterceptor.setDialectType(pageInterceptorDialectType);
            }
        }

        return paginationInterceptor;
    }

    @Bean
    public DataScopeInterceptor dataScopeInterceptor() {
        return new DataScopeInterceptor();
    }

}
