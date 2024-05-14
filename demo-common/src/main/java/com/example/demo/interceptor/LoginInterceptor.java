package com.example.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:26
 * @Version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Value("${spring.application.login.url:''}")
    private String loginUrl;

    public LoginInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//        JSONObject jsonUser = (JSONObject) session.getAttribute("_Demo_Session_Token_");
//        if (jsonUser == null) {
//            if (StringUtils.isNotBlank(this.loginUrl)) {
//                if (!this.loginUrl.startsWith("/")) {
//                    this.loginUrl = "/" + this.loginUrl;
//                }
//                response.sendRedirect(request.getContextPath() + this.loginUrl);
//            } else {
//                response.sendRedirect(request.getContextPath() + "/login");
//            }
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
