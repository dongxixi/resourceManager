package com.lovo.fire_company.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorConfig implements WebMvcConfigurer {

    //静态资源的放行
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/staticResources/**")//放行的资源
                .addResourceLocations("classpath:/staticResources/");//资源所在的本地位置

    }

    //添加多个拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        //注册权限的拦截器
        registry.addInterceptor(new PowerInterceptor()).addPathPatterns("/**").excludePathPatterns("/gotoLogin").
                excludePathPatterns("/loginyanzheng").excludePathPatterns("/loginMessage").excludePathPatterns("/staticResources/**").
                excludePathPatterns("/getResourceStatisticsJson");
    }

}
