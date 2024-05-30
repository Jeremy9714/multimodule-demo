package com.example.demo.utils.web;

import cn.hutool.core.util.ObjectUtil;
import com.example.demo.utils.bean.CommonBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/30 8:47
 * @Version: 1.0
 */
public class HttpClientUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final CloseableHttpClient httpClient;

    static {
        // 注册访问协议相关的Socket工场
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                .build();

        // HttpConnection工场
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new
                ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE,
                DefaultHttpResponseParserFactory.INSTANCE);
        //DNS解析器
        DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

        //创建池化连接管理器
        PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);
        //默认为Socket配置
        SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        poolConnManager.setDefaultSocketConfig(defaultSocketConfig);

        // 设置整个连接池的最大连接数
        poolConnManager.setMaxTotal(1000);
        // 每个路由的默认最大连接，每个路由实际最大连接默认为DefaultMaxPerRoute控制，maxTotal是整个池子最大数
        // DefaultMaxPerRoute设置过小无法支持大并发（ConnectPoolTimeoutException: Timeout waiting for connect from pool) 路由是maxTotal的细分
        //每个路由最大连接数
        poolConnManager.setDefaultMaxPerRoute(1000);
        //在从连接池获取连接时，连接不活跃多长时间后需要一次验证，默认2S
        poolConnManager.setValidateAfterInactivity(5 * 1000);

        //默认请求配置
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接超时时间
                .setConnectTimeout(200 * 1000)
                //设置等待数据超时时间
                .setSocketTimeout(300 * 1000)
                //设置从连接池获取连接的等待超时时间
                .setConnectionRequestTimeout(200000)
                .build();

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setConnectionManager(poolConnManager)
                //设置连接池不是共享模式
                .setConnectionManagerShared(false)
                //定期回调空闲连接
                .evictIdleConnections(60, TimeUnit.SECONDS)
                //定期回收过期
                .evictExpiredConnections()
                //连接存活时间，如果不设置，根据长连接信息决定
                .setConnectionTimeToLive(6000, TimeUnit.SECONDS)
                //设置默认请求配置
                .setDefaultRequestConfig(requestConfig)
                // 连接重试策略，是否能keepalive
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                //长连接配置，即获取长连接生产多少时间
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                //设置重试次数，默认是3次；当前是禁用
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));

        httpClient = httpClientBuilder.build();

        //JVM停止或重启时，关闭连接池释放连接
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭HttpClient异常", e);
            }
        }));
    }

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * 不携带header信息
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * 不携带header信息
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, null, params);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * 携带header信息
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> header, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        String result = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet;
        // 拼接query
        if (ObjectUtil.isNotNull(params)) {
            int i = 0;
            for (String key : params.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(params.get(key));
                ++i;
            }
            apiUrl += param;
        }
        httpGet = new HttpGet(apiUrl);
        // 添加header
        if (ObjectUtil.isNotNull(header)) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpGet.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            } else {
                log.info("调用接口异常{}", response);
                //不推荐使用CloseableHttpResponse.close关闭连接，他将直接关闭Socket，导致长连接不能复用
                EntityUtils.consume(response.getEntity());
            }
            if (log.isDebugEnabled()) {
                String logsb = "##############\n" +
                        "# 发送报文：" + getPlainText(params) + "\n" +
                        "# 响应代码：" + statusCode + "\n" +
                        "# 响应报文：" + result + "\n" +
                        "#############################################################\n";
                log.debug(logsb);
            }
        } catch (IOException e) {
            try {
                if (null != response) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException ioException) {
                log.error("调用接口异常", ioException);
            }
            log.error("调用接口异常", e);
        }
        return result;
    }

    /**
     * 发送json的post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doJSONPost(String url, Map<String, Object> params) {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        return doPost(url, header, params);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String doPost(String url, Map<String, Object> header, Map<String, Object> params) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        if (ObjectUtil.isNotNull(header)) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        if (ObjectUtil.isNotNull(params)) {
            StringEntity paramEntity = new StringEntity(CommonBeanUtils.beanToJson(params), StandardCharsets.UTF_8);
            httpPost.setEntity(paramEntity);
        }
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            } else {
                log.error("调用接口异常{}", response);
                EntityUtils.consume(response.getEntity());
            }
            if (log.isDebugEnabled()) {
                String logsb = "##############\n" +
                        "# 发送报文：" + getPlainText(params) + "\n" +
                        "# 响应代码：" + statusCode + "\n" +
                        "# 响应报文：" + result + "\n" +
                        "#############################################################\n";
                log.debug(logsb);
            }
        } catch (IOException e) {
            try {
                if (null != response) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException ioException) {
                throw new RuntimeException("调用接口异常", ioException);
            }
            throw new RuntimeException("调用接口异常", e);
        }
        return result;
    }

    /**
     * 发送json的post请求
     *
     * @param url
     * @param value
     * @return
     */
    public static String doJSONPostString(String url, String value) {
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        return doJSONPostString(url, header, value);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param url
     * @param header
     * @param value
     * @return
     */
    public static String doJSONPostString(String url, Map<String, Object> header, String value) {
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        if (ObjectUtil.isNotNull(header)) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        if (StringUtils.isNotBlank(value)) {
            StringEntity paramEntity = new StringEntity(value, StandardCharsets.UTF_8);
            httpPost.setEntity(paramEntity);
        }
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                } else {
                    log.error("调用接口异常{}", response);
                    EntityUtils.consume(response.getEntity());
                }
            }
            if (log.isDebugEnabled()) {
                String logsb = "##################\n" +
                        "# 发送报文：" + value + "\n" +
                        "# 响应代码：" + statusCode + "\n" +
                        "# 响应报文：" + result + "\n" +
                        "########################################################################\n";
                log.debug(logsb);
            }
        } catch (IOException e) {
            try {
                if (null != response) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException ioException) {
                throw new RuntimeException("调用接口异常", ioException);
            }
            throw new RuntimeException("调用接口异常", e);
        }
        return result;
    }

    /**
     * 发送Put请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doPut(String url, Map<String, Object> param) {
        return doPut(url, null, param);
    }

    /**
     * 发送Put请求
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static String doPut(String url, Map<String, Object> header, Map<String, Object> params) {
        String result = null;
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        if (ObjectUtil.isNotNull(header)) {
            Set<Map.Entry<String, Object>> entries = header.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                httpPut.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        if (ObjectUtil.isNotNull(params)) {
            StringEntity paramEntity = new StringEntity(CommonBeanUtils.beanToJson(params), StandardCharsets.UTF_8);
            httpPut.setEntity(paramEntity);
        }
        try {
            response = httpClient.execute(httpPut);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } else {
                log.error("调用接口异常{}", response);
                EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {
            try {
                if (null != response)
                    EntityUtils.consume(response.getEntity());
            } catch (IOException e1) {
                log.error("调用接口异常", e1);
            }
            log.error("调用接口异常", e);
        }
        return result;
    }

    /**
     * 发送Put请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPutFormData(String url, Map<String, Object> params) {
        String result = null;
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()), ContentType.MULTIPART_FORM_DATA.withCharset("UTF-8"));
            }
            HttpEntity multipart = builder.build();
            httpPut.setEntity(multipart);
            response = httpClient.execute(httpPut);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } else {
                log.error("调用接口异常{}", response);
                EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {
            try {
                if (null != response)
                    EntityUtils.consume(response.getEntity());
            } catch (IOException e1) {
                log.error("调用接口异常", e1);
            }
            log.error("调用接口异常", e);
        }
        return result;
    }

    /**
     * 发送Delete请求
     *
     * @param url
     * @param header
     * @return
     */
    public static String doDelete(String url, Map<String, Object> header) {
        String result = null;
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpResponse response = null;
        try {
            Set<Map.Entry<String, Object>> entries = header.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                httpDelete.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
            response = httpClient.execute(httpDelete);
            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } else {
                log.error("调用接口异常{}", response);
                //不推荐使用CloseableHttpResponse.close关闭连接，他将直接关闭Socket，导致长连接不能复用
                EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {
            try {
                if (null != response)
                    EntityUtils.consume(response.getEntity());
            } catch (IOException e1) {
                log.error("调用接口异常", e1);
            }
            log.error("调用接口异常", e);
        }
        return result;
    }

    /**
     * map格式化
     *
     * @param map
     * @return
     */
    public static String getPlainText(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
