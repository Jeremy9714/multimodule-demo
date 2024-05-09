package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 14:29
 * @Version: 1.0
 */
@Slf4j
public abstract class BaseController {

    @Autowired
    public MessageSource messageSource;
    @Autowired
    private HttpServletRequest request;
    @Value("${spring.profiles.active:}")
    private String profile;
    protected static final String SUCCESS = "1";
    protected static final String ERROR = "0";

    public BaseController() {
    }

    public String getProfile() {
        return this.profile;
    }

    public String getView(String viewPath, ModelMap map) {
        map.put("base", this.request.getContextPath());
        map.put("profile", this.profile);
        return viewPath;
    }

    public void renderJson(HttpServletResponse response, String text) {
        this.render(response, "application/json;charset=UTF-8", text);
    }

    public void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        // 浏览器不用缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void ajaxDoneSuccess(HttpServletResponse response, String message) {
        JSONObject result = new JSONObject();
        result.put("state", 1);
        result.put("statusCode", 200);
        if (StringUtils.isEmpty(message)) {
            result.put("msg", this.getMessage("webfinal.action.failure"));
        } else {
            result.put("msg", message);
        }
        this.renderJson(response, result.toString());
    }

    public void ajaxDoneError(HttpServletResponse response, String message) {
        JSONObject result = new JSONObject();
        result.put("state", 0);
        result.put("statusCode", 300);
        if (StringUtils.isEmpty(message)) {
            result.put("msg", this.getMessage("webfinal.action.failure"));
        } else {
            result.put("msg", message);
        }
        this.renderJson(response, result.toString());
    }

    public String getMessage(String code) {
        Locale locale = LocaleContextHolder.getLocale();
        return this.messageSource.getMessage(code, (Object[]) null, locale);
    }

}
