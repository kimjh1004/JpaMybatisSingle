package com.boot.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.boot.conf.Interceptor.LoginInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	 
         registry.addInterceptor(new LoginInterceptor())
                 .addPathPatterns("/**")
                 .excludePathPatterns("/login/**", "/assets/**", "/images/**", "/js/**", "/css/**");
    }
}