package com.mytaotao.web.intercoper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytaotao.web.pojo.User;
import com.mytaotao.web.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liqiyu on 2016/8/20 0020.
 */
public class MytaotaoInterceptor implements HandlerInterceptor {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final ThreadLocal<User> threadLocal = new ThreadLocal<>();
    @Autowired
    ApiService apiService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if ("token".equals(c.getName())) {
                cookie = c;
            }
        }
        if (cookie == null) {
            //未登录,跳转登录页
            response.sendRedirect("http://sso.mytaotao.com/user/login.html");
            return false;
        }
        String jsonData = apiService.doget("http://sso.mytaotao.com/service/user/query/" + cookie.getValue());
        if (StringUtils.isEmpty(jsonData)) {
            //未登录,跳转登录页
            response.sendRedirect("http://sso.mytaotao.com/user/login.html");
            return false;
        }
        User user = null;
        try {
            user = MAPPER.readValue(jsonData, User.class);
        } catch (IOException e) {
            //解析异常,跳转登录页
            e.printStackTrace();
            response.sendRedirect("http://sso.mytaotao.com/user/login.html");
            return false;
        }
        threadLocal.set(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        threadLocal.set(null);
    }
}
