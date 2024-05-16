package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:42
 * @Version: 1.0
 */
public final class Server {
//    private static final Logger jdField_new = LoggerFactory.getLogger(Server.class);
//
//    public Server() {
//    }
//
//    public static void initServer(WebApplicationContext paramWebApplicationContext) {
//        Properties properties = new Properties();
//
//        try {
//            String pathGetClass = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            if (jdField_new.isDebugEnabled()) {
//                jdField_new.debug(pathGetClass);
//            }
//
//            int domainIndex;
//            if (pathGetClass.indexOf("webapps") == -1) {
//                if (pathGetClass.indexOf("/domains") != -1) {
//                    domainIndex = pathGetClass.indexOf("/domains");
//                    pathGetClass = pathGetClass.substring(0, domainIndex);
//                } else if (pathGetClass.indexOf(".jar") != -1) {
//                    pathGetClass = pathGetClass.substring(0, pathGetClass.indexOf(".jar"));
//                    pathGetClass = pathGetClass.substring(0, pathGetClass.lastIndexOf("/"));
//                    pathGetClass = pathGetClass.substring(5, pathGetClass.length());
//                }
//            } else {
//                for(domainIndex = 0; domainIndex < 6; ++domainIndex) {
//                    pathGetClass = pathGetClass.substring(0, pathGetClass.lastIndexOf("/"));
//                }
//            }
//
//            if (jdField_new.isDebugEnabled()) {
//                jdField_new.debug("配置文件license.properties路径" + pathGetClass + "======");
//            }
//
//            String filepath = pathGetClass + "/license.properties";
//            Object inputStream = null;
//
//            try {
//                if (jdField_new.isDebugEnabled()) {
//                    jdField_new.debug("FileInputStream读取配置文件license.properties" + filepath);
//                }
//
//                inputStream = new FileInputStream(filepath);
//            } catch (Exception var8) {
//                if (jdField_new.isDebugEnabled()) {
//                    jdField_new.debug("FileInputStream读取配置文件失败，读取路径：" + filepath);
//                }
//            }
//
//            if (inputStream == null) {
//                if (jdField_new.isDebugEnabled()) {
//                    jdField_new.debug("getContextClassLoader读取配置文件classpath:license.properties");
//                }
//
//                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("license.properties");
//            }
//
//            if (inputStream == null) {
//                if (jdField_new.isDebugEnabled()) {
//                    jdField_new.debug("paramWebApplicationContext读取配置文件classpath:license.properties");
//                }
//
//                inputStream = paramWebApplicationContext.getClassLoader().getResourceAsStream("license.properties");
//            }
//
//            properties.load((InputStream)inputStream);
//            String appCode = properties.getProperty("spring.license.application.code");
//            String profile = properties.getProperty("spring.license.profile");
//            SystemApp.initApplication(profile);
//            if (jdField_new.isDebugEnabled()) {
//                jdField_new.debug("Application is Initialize..., Run in[" + appCode.toUpperCase() + "] mode");
//            }
//        } catch (Exception var9) {
//            var9.printStackTrace();
//
//            try {
//                Thread.sleep(5000L);
//            } catch (InterruptedException var7) {
//                var7.printStackTrace();
//            }
//
//            System.setProperty("license", "500");
//        }
//
//    }
//
//    public static void main(String[] args) {
//        String pathGetClass = "/opt/server/apache-tomcat-7.0.92/webapps/ODWeb/WEB-INF/lib/application-license-api-1.0.0-SNAPSHOT.jar!";
//
//        for(int i = 0; i < 6; ++i) {
//            pathGetClass = pathGetClass.substring(0, pathGetClass.lastIndexOf("/"));
//        }
//
//        System.out.println(pathGetClass);
//    }
}
