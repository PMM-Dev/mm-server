package com.kwon770.mm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register")
//                .addPathPatterns("/api/user/**")
                .addPathPatterns("/api/title/**")
//                .addPathPatterns("/api/restaurant/**")
//                .addPathPatterns("/api/review/**")
                .addPathPatterns("/api/theme/**")
                .addPathPatterns("/api/special/**");
    }
}
