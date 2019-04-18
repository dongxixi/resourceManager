package com.lovo.hospital.config;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class InterceptorConfig extends WebMvcConfigurerAdapter {


    //静态资源的放行
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/staticResources/**")//放行的资源
        .addResourceLocations("classpath:/staticResources/");//资源所在的本地位置

    }
}
