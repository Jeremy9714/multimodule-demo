package com.example.demo.interceptor.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/13 14:29
 * @Version: 1.0
 */
public class MyLoginInterceptor implements HandlerInterceptor {

    @Value("${global.login.url:}")
    private String loginUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("======loginInterceptor拦截请求[" + request.getRequestURI() + "]=====");
        HttpSession session = request.getSession();
        JSONObject userinfo = (JSONObject) session.getAttribute("USERINFO");
        if (userinfo == null) {
            if (StringUtils.isNotBlank(loginUrl)) {
                if (!loginUrl.startsWith("/")) {
                    this.loginUrl = "/" + this.loginUrl;
                }
                response.sendRedirect(request.getContextPath() + this.loginUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
