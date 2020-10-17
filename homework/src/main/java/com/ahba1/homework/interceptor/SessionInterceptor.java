package com.ahba1.homework.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object o, Exception exception)
            throws Exception {
    }
    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object o, ModelAndView modelAndView)
            throws Exception {

    }
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
        //登录放行
        if (req.getRequestURI().equals("/login")||req.getRequestURI().equals("/activate")) {
            return true;
        }
        //重定向
        Object object = req.getSession().getAttribute("user");
        if (null == object) {
            return false;
        }
        return true;
    }

}
