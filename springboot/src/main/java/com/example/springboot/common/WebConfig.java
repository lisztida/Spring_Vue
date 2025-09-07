package com.example.springboot.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor()).
                addPathPatterns("/**")
                .excludePathPatterns("/login","/register");
        //除了login和register以外的页面都要做验证，因为login是没有token时访问的，register是没有账号时访问的
    }

    @Bean
    public JWTInterceptor jwtInterceptor(){
        return new JWTInterceptor();

    }


}
