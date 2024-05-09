package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/9 15:52
 * @Version: 1.0
 */
public class XttpServletRequest extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public String getParameter(String name) {
        return XssShieldUtils.stripXss(super.getParameter(XssShieldUtils.stripXss(name)));
    }

    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(XssShieldUtils.stripXss(name));
        if (values != null) {
            for (int i = 0; i < values.length; ++i) {
                values[i] = XssShieldUtils.stripXss(values[i]);
            }
        }
        return values;
    }
}
