package com.example.demo.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 16:02
 * @Version: 1.0
 */
public final class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    }

    @Override
    public void destroy() {
    }
}
