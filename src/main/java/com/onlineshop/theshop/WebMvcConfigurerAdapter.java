package com.onlineshop.theshop;

import com.onlineshop.theshop.interceptor.ShopInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    ShopInterceptor shopInterceptor;

    @Bean
    public ShopInterceptor shopInterceptor(){
        return new ShopInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(shopInterceptor).excludePathPatterns(
                "/login",
                "/signup",
                "/signupAddress",
                "/error",
                "/adduser.post",
                "/templates/**",
                "/css/**",
                "/test",
                "/h2",
                "/h2/**",
                "/files/**",
                "/user/delete.post",
                "/dist/**",
                "/uploadFile",
                "/selectFile",
                "/logout"
                ).pathMatcher(new AntPathMatcher());
    }
}
