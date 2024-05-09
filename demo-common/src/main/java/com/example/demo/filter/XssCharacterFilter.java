package com.example.demo.filter;

import com.example.demo.utils.XttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:49
 * @Version: 1.0
 */
public class XssCharacterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            request = new XttpServletRequest(request);
            chain.doFilter(request, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
