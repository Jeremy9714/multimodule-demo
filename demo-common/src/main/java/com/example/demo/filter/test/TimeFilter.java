package com.example.demo.filter.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 10:54
 * @Version: 1.0
 */
//@Order(1)
//@WebFilter(filterName = "timeFilter", urlPatterns = "/*", description = "时间拦截器")
//@Component
public class TimeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        System.out.println("======timeFilter拦截======");
        chain.doFilter(request, response);
        long endTime = System.currentTimeMillis();
        System.out.println("======timeFilter放行: " + (endTime - startTime) / 1000 + "======");
    }
}
