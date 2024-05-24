package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.JavaScriptUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    public Map<String, Object> getParameter(HttpServletRequest req) {
        Map<String, Object> map = new HashMap();
        Enumeration pNames = req.getParameterNames();

        while(pNames.hasMoreElements()) {
            String key = (String)pNames.nextElement();
            String value = this.getPara(key);
            map.put(key, value);
        }

        return map;
    }

    public String getPara(String name) {
        String str = this.request.getParameter(name);
        if (StringUtils.isEmpty(str)) {
            return str;
        } else {
            str = this.escapeSql(str);
            str = HtmlUtils.htmlEscape(str);
            str = JavaScriptUtils.javaScriptEscape(str);
            return str;
        }
    }

    public String getHeader(String name) {
        String str = this.request.getHeader(name);
        if (StringUtils.isEmpty(str)) {
            return str;
        } else {
            str = this.escapeSql(str);
            str = HtmlUtils.htmlEscape(str);
            str = JavaScriptUtils.javaScriptEscape(str);
            return str;
        }
    }

    public String[] getParaMap(String name) {
        String[] param = this.request.getParameterValues(name);
        if (param != null && param.length > 0) {
            String[] var3 = param;
            int var4 = param.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String str = var3[var5];
                str = this.escapeSql(str);
                str = HtmlUtils.htmlEscape(str);
                str = JavaScriptUtils.javaScriptEscape(str);
            }
        }

        return param;
    }

    public Integer getParaInt(String name) {
        return this.toInt(this.getPara(name), 0);
    }

    public Integer getParaToInt(String name, Integer defaultValue) {
        return this.toInt(this.getPara(name), defaultValue);
    }

    public Long getParaToLong(String name) {
        return this.toLong(this.getPara(name), (Long)null);
    }

    public Long getParaToLong(String name, Long defaultValue) {
        return this.toLong(this.getPara(name), defaultValue);
    }

    private Integer toInt(String value, Integer defaultValue) {
        try {
            if (value != null && !"".equals(value.trim())) {
                value = value.trim();
                return !value.startsWith("N") && !value.startsWith("n") ? Integer.parseInt(value) : -Integer.parseInt(value.substring(1));
            } else {
                return defaultValue;
            }
        } catch (RuntimeException var4) {
            var4.printStackTrace();
            throw new BaseException(404, "Can not parse the parameter \"" + value + "\" to Integer value.");
        } catch (Exception var5) {
            throw new BaseException(404, "Can not parse the parameter \"" + value + "\" to Integer value.");
        }
    }

    private Long toLong(String value, Long defaultValue) {
        try {
            if (value != null && !"".equals(value.trim())) {
                value = value.trim();
                return !value.startsWith("N") && !value.startsWith("n") ? Long.parseLong(value) : -Long.parseLong(value.substring(1));
            } else {
                return defaultValue;
            }
        } catch (Exception var4) {
            throw new BaseException(404, "Can not parse the parameter \"" + value + "\" to Long value.");
        }
    }

    public String getPara(String name, String defaultValue) {
        String result = this.getPara(name);
        return result != null && !"".equals(result) ? result : defaultValue;
    }

    public String escapeSql(String str) {
        return str == null ? null : StringUtils.replace(str, "'", "''");
    }

}
