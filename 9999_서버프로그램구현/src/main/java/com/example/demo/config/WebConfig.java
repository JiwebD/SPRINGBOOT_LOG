package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 웹 브라우저에서 /upload/** 로 접근하면
        // 실제 파일 시스템 c:/upload/ 디렉토리에서 파일을 찾게 한다
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///c:/upload/");
    }
}
