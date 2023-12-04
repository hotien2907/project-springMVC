package com.example.config;


import com.example.dto.response.RespUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();

        RespUser admin =(RespUser) httpSession.getAttribute("admin");

        if(admin != null){
            return true;
        }
        response.sendRedirect("/login-admin");
        return false;

    }
}
