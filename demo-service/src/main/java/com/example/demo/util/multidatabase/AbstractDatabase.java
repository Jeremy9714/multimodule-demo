package com.example.demo.util.multidatabase;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.multidatabase.exception.DatabaseException;
import com.example.demo.util.multidatabase.exception.ExceptionEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.telnet.TelnetClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketException;
import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/14 13:52
 * @Version: 1.0
 */
public abstract class AbstractDatabase implements IDatabase {
    private static Logger logger = LoggerFactory.getLogger(AbstractDatabase.class);

    /**
     * 私有属性定义区
     */
    private DruidDataSource druidDataSource;    // Druid连接池对象     ->abstract处理

    private String databaseName;                // 数据库名称	         ->外部传入构造函数             
    private String host;                        // IP地址             ->外部传入构造函数
    private Integer port;                       // 端口号             ->外部传入构造函数
    private String username;                    // 用户名             ->外部传入构造函数
    private String password;                    // 密码               ->外部传入构造函数
    private String jdbcType;                    // mysql、oracle使用
    private JSONObject extInfo;

    public AbstractDatabase(String databaseName, String host, Integer port, String username, String password, String jdbcType, JSONObject extInfo) {
        super();
        this.databaseName = databaseName;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.jdbcType = jdbcType;
        this.extInfo = extInfo;
    }

    public DruidDataSource getDruidDataSource() {
        return druidDataSource;
    }

    public void setDruidDataSource(DruidDataSource druidDataSource) {
        this.druidDataSource = druidDataSource;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public JSONObject getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(JSONObject extInfo) {
        this.extInfo = extInfo;
    }

    /**
     * 子类继承方法区
     */
    public abstract String handleJdbcUrl(String jdbcType);

    public abstract String handleDriver();

    public abstract String handleSchema();

    public abstract Map<String, Object> handleFormat();

    public abstract void handleRemarks(Connection conn);

    public abstract void handleColumnType(Connection conn, ResultSet rs, Map<String, Object> resultMap);

    public abstract void handleDataType(Connection conn, List<String> typeNameList, List<Map<String, Object>> typeList);

    public abstract String handlePageSql(String sql, int firstIndex, int pageSize);

    public abstract String handleTbName(String tableName);

    @Override
    public boolean canConnect() {
        TelnetClient telnetClient = new TelnetClient();
        try {
            telnetClient.connect(this.host, this.port);
            return telnetClient.isConnected();
        } catch (IOException e) {
            logger.error("ip地址和端口号联不通", e);
            return false;
        } finally {
            try {
                telnetClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean closeDatasource() {
        try {
            if (druidDataSource != null) {
                druidDataSource.close();
                druidDataSource = null;
            }
            return true;
        } catch (Exception e) {
            logger.error("关闭连接池异常", e);
            return false;
        }
    }

    @Override
    public JSONObject isDatabaseConnected() {
        JSONObject result = new JSONObject();
        result.put("connectStatus", false);
        if (!this.canConnect()) {
            result.put("errorMessage", "数据库的IP和端口无法联通，请检查！");
            return result;
        }
        String driver = this.handleDriver();
        Connection conn = null;
        try {
            Class.forName(driver);
            DriverManager.setLoginTimeout(5);
            conn = DriverManager.getConnection(this.handleJdbcUrl(jdbcType), this.username, this.password);
            result.put("connectStatus", true);
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            String cause = e.getCause().getLocalizedMessage();
            String message = e.getMessage();
            result.put("errorMessage", "数据库无法连接，异常信息如下: " + (StringUtils.isNotBlank(cause) ? cause : message));
            return result;
        } finally {
            closeObject(conn, null, null);
        }
    }

    @Override
    public void executeSql(String sql) {
        
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param psmt
     * @param rs
     */
    public void closeObject(Connection conn, PreparedStatement psmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("closeObj: {}", ex);
                throw new DatabaseException(ExceptionEnum.CLOSE_CONNECTION_EXCEPTION, "closeObj", "关闭rs出现异常: " + ex);
            }
        }

        if (psmt != null) {
            try {
                psmt.close();
            } catch (SQLException ex) {
                logger.error("closeObj: {}", ex);
                throw new DatabaseException(ExceptionEnum.CLOSE_CONNECTION_EXCEPTION, "closeObj", "关闭psmt出现异常: " + ex);
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.error("closeObj: {}", ex);
                throw new DatabaseException(ExceptionEnum.CLOSE_CONNECTION_EXCEPTION, "closeObj", "关闭conn出现异常: " + ex);
            }
        }
    }

}
