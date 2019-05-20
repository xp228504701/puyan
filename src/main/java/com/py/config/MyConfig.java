package com.py.config;

import com.py.interceptor.H5IndexInterceptor;
import com.py.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private H5IndexInterceptor h5IndexInterceptor;


    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/file/**").addResourceLocations("file:C:/upload/file/");
    }


    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login") 表示除了登陆之外，因为登陆不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/jumpLogin","/loginAdmin","/api/**","/file/**","/","/webjars/**","/common/**","/css/**","/img/**","/jquery.1.9.1/**","/js/**","/layui/**","/login/**");

//        registry.addInterceptor(h5IndexInterceptor).addPathPatterns("/h5/**")
//                .excludePathPatterns("/jumpH5Index");
    }





}
