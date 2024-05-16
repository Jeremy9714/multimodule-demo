package com.example.demo.listener;

import com.example.demo.utils.Server;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:41
 * @Version: 1.0
 */
//@WebListener
public class ApplicationListener implements ServletContextListener {
//    public ApplicationListener() {
//    }
//
//    public void contextInitialized(ServletContextEvent event) {
//        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
//        Server.initServer(webApplicationContext);
//    }
//
//    public void contextDestroyed(ServletContextEvent sce) {
//    }
}
