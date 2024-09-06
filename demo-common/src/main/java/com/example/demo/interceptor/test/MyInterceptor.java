package com.example.demo.interceptor.test;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 11:23
 * @Version: 1.0
 */
@Component
public class MyInterceptor implements HandlerInterceptor {

    /**
     * 处理请求之前进行调用 (controller方法调用前)
     * 返回值：true表示继续流程, false表示流程终端, 不会继续调用其他拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("======拦截器开始拦截======");
        return false;
    }

    /**
     * 请求处理之后进行调用, 但在视图被渲染之前 (controller方法已调用)
     * 可以通过modelAndView对数据模型和视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("======return前======");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("======操作完毕, 可以用于资源清理======");
    }
}
