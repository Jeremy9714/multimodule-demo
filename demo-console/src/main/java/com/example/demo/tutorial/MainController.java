package com.example.demo.tutorial;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.controller.BaseController;
import com.example.demo.tutorial.login.entity.UserEntity;
import com.example.demo.tutorial.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/6 9:35
 * @Version: 1.0
 */
@RefreshScope
@Controller
@Slf4j
public class MainController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        HttpSession session = request.getSession();
        JSONObject userinfo = (JSONObject) session.getAttribute("USERINFO");
        if (userinfo != null) {
            String username = userinfo.getString("ACCOUNT");
            String password = userinfo.getString("PWD");
            return this.loginHandler(request, response, map, username, password);
        } else {
            return this.login(request, response, map);
        }
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        return this.getView("login", map);
    }

    @PostMapping("/index")
    public String main(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        try {
            request.getSession().invalidate();
            if (request.getCookies() != null) {
                Cookie cookie = request.getCookies()[0];
                cookie.setMaxAge(0);
            }
        } catch (Exception e) {
            log.error("error", e);
        }

        String username = this.getPara("username");
        String password = this.getPara("password");
        return this.loginHandler(request, response, map, username, password);
    }

    @SuppressWarnings("unchecked")
    public String loginHandler(HttpServletRequest request, HttpServletResponse response, ModelMap map, String username, String password) {
        UserEntity userEntity = this.loginService.userLogin(username, password);
        if (userEntity != null) {
            HttpSession session = request.getSession(true);
            JSONObject user = new JSONObject();
            user.put("ACCOUNT", userEntity.getAccount());
            user.put("PWD", userEntity.getPassword());
            session.setAttribute("USERINFO", user);
            map.put("currentUser", user);
            map.put("msg", "登陆成功");
            return this.getView("index", map);
        } else {
            map.put("error", "登录失败");
            return this.login(request, response, map);
        }
    }
}
