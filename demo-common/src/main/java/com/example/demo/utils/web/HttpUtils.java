package com.example.demo.utils.web;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.https.Handler;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/31 9:17
 * @Version: 1.0
 */
public final class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
    }

    /**
     * get请求
     *
     * @param getUrl
     * @param cookie
     * @return
     */
    public static JSONObject exeHttpGetMethod(String getUrl, String cookie) {
        if (log.isDebugEnabled()) {
            log.debug("-------------请求地址:{}", getUrl);
        }
        JSONObject jsonObject = new JSONObject();
        URL url;
        InputStream inputStream = null;
        InputStreamReader inputReader = null;
        BufferedReader in = null;
        try {
            getUrl = getUrl.replaceAll(" ", "%20");
            url = new URL(getUrl);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpUrlConn = (HttpURLConnection) urlConn;
            httpUrlConn.setRequestProperty("Content-Type", "*/*; charset=utf-8");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
//            httpUrlConn.setRequestProperty("Accept","text/plain; charset=utf-8");
            if (StringUtils.isNotBlank(cookie)) {
                httpUrlConn.setRequestProperty("Cookie", cookie);
            }
            httpUrlConn.setDoInput(true); // 输入
            httpUrlConn.setDoOutput(false); // 输出
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("GET");

//            httpUrlConn.connect(); // 连接
//            int responseCode = httpUrlConn.getResponseCode(); // 响应码
//            if(HttpURLConnection.HTTP_OK == responseCode){}

            StringBuffer sb = new StringBuffer();
            inputStream = httpUrlConn.getInputStream();
            inputReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            in = new BufferedReader(inputReader);
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            jsonObject = JSONObject.parseObject(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    /**
     * post请求
     *
     * @param postUrl
     * @param post
     * @param cookie
     * @return
     */
    public static JSONObject exePostHttpMethod(String postUrl, String post, String cookie) {
        if (log.isDebugEnabled()) {
            log.debug("-----------------请求地址:{}", postUrl);
        }
        JSONObject jsonObject = new JSONObject();
        PrintWriter out = null;
        OutputStream outputStream = null;
        BufferedReader in = null;
        InputStreamReader inputReader = null;
        InputStream inputStream = null;
        URL url;
        try {
            postUrl = postUrl.replaceAll(" ", "%20");
            url = new URL(postUrl);
            URLConnection urlConn = url.openConnection();
            HttpURLConnection httpUrlConn = (HttpURLConnection) urlConn;
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post不用缓存
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setRequestProperty("Content-Type", "application/json");
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            if (StringUtils.isNotBlank(cookie)) {
                httpUrlConn.setRequestProperty("Cookie", cookie);
            }
            httpUrlConn.connect();
            outputStream = httpUrlConn.getOutputStream();
            out = new PrintWriter(outputStream);
            out.write(post);
            out.flush();
            int responseCode = httpUrlConn.getResponseCode();
            StringBuffer sb = new StringBuffer();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                inputStream = httpUrlConn.getInputStream();
                inputReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                in = new BufferedReader(inputReader);
                String line;
                while ((line = in.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                jsonObject = JSONObject.parseObject(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }

    /**
     * 文件上传
     *
     * @param uploadUrl
     * @param fileUrl
     * @return
     */
    public static void exeFileUploadMethod(String uploadUrl, String fileUrl) {

    }

    public static JSONObject uploadFile(String actionUrl, InputStream fStream, Map<String, Object> fileInfo, String filename) {
        String end = "\r\n";
        String boundary = "*****";
        String twoHyphens = "--";
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        DataOutputStream dos = null;
        try {
            URL url;
            if (actionUrl.startsWith("https")) {
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                TrustManager[] tm = {new MyX509TrustManager()};
                sslContext.init(null, tm, new SecureRandom());
                HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                    public boolean verify(String s, SSLSession sslsession) {
                        System.out.println("WARNING: Hostname is not matched for cert.");
                        return true;
                    }
                };
                HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                url = new URL(null, actionUrl, new Handler());
            } else {
                url = new URL(actionUrl);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("post");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            httpURLConnection.setConnectTimeout(600000);
            httpURLConnection.setReadTimeout(6000000);
            httpURLConnection.setChunkedStreamingMode(0);

            dos = new DataOutputStream(httpURLConnection.getOutputStream());

            // 拼接表格值
            Set<String> keys = fileInfo.keySet();
            for (String key : keys) {
                dos.writeBytes(twoHyphens + boundary);
                dos.writeBytes(end);
                dos.writeBytes("Content-Disposition:form-data;name=\"");
                dos.write(URLEncoder.encode(key, "UTF-8").getBytes());
                dos.writeBytes("\"" + end);
                dos.writeBytes(end);
                dos.write(fileInfo.get(key).toString().getBytes());
                dos.writeBytes(end);
            }

            // 拼接文件
            dos.writeBytes(twoHyphens + boundary);
            dos.writeBytes(end);
            dos.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\"" +
                    URLEncoder.encode(filename, "UTF-8") + "\"" + end);
            dos.writeBytes(end);

            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                dos.write(buffer, 0, length);
            }
            dos.writeBytes(end);

            fStream.close();
            dos.writeBytes(twoHyphens + end + twoHyphens);
            dos.writeBytes(end);
            dos.flush();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 输入流转byte数组
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        return outStream.toByteArray();
    }

    public static class MyX509TrustManager implements X509TrustManager {

        // 客户端校验
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        // 服务端校验
        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
