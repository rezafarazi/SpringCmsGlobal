package com.sorapp.jowncms.jowncms.Config;

import com.sorapp.jowncms.jowncms.Middleware.AdminAuthCheckMiddleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

    //Admin Auth Check Start
    @Autowired
    public AdminAuthCheckMiddleware AdminAuthCheck;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(AdminAuthCheck).addPathPatterns("/Admin/Panel/*");
    }
    //Admin Auth Check End

}
