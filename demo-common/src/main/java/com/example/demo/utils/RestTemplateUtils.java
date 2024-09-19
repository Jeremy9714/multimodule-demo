package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.*;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/19 14:20
 * @Version: 1.0
 */
@Slf4j
public class RestTemplateUtils {

    private static String SSLPwd;

    // https无证书
    public static RestTemplate getNoSSLHttpsRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        // 忽略ssl证书验证
        SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, (chain, authType) -> true)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Value("${SSL.password:}")
    public void setSSLPwd(String SSLPwd) {
        this.SSLPwd = SSLPwd;
    }
}
