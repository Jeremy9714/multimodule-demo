package com.example.demo.filter.test;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 10:58
 * @Version: 1.0
 */
public class RegistrationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("======registrationBean拦截了方法======");
        chain.doFilter(request, response);
        System.out.println("======registrationBean放行方法======");
    }
}
