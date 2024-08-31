package com.example.movie;

import com.example.movie.login.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                // 로그인 상관 없이 접속 가능한 url 목록 / 테스트 시 포합시켜서
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error",
                        "/movie", "/movieinfo", "/");
    }

}
