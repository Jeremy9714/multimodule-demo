package com.example.demo.filter.test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 10:41
 * @Version: 1.0
 */
//@WebFilter(filterName = "filter1", urlPatterns = "/multidb/*")
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("======filter1拦截到了请求======");
        chain.doFilter(request, response);
        System.out.println("======filter1放行后逻辑======");
    }
}
