package com.boot.conf.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.stereotype.Component;
 

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
 
    // 요청을 컨트롤러에 보내기 전 작업
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        
        log.info("LoginInterceptor - {}", "호출완료");
        
        HttpSession session = request.getSession();
        String memberId = (String)session.getAttribute("memberId");
        
        if(memberId != null) {
            return true;
        } else {
        	response.sendRedirect("/login"); 
            return false;
        }
    }      
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}