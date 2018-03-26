package org.softuni.wms.config;

import org.softuni.wms.interseptors.FirstUserInterceptor;
import org.softuni.wms.interseptors.LastModelAndViewInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final FirstUserInterceptor firstUserInterceptor;
    private final LastModelAndViewInterceptor lastModelAndViewInterceptor;

    @Autowired
    public WebMvcConfiguration(FirstUserInterceptor firstUserInterceptor, LastModelAndViewInterceptor lastModelAndViewInterceptor) {
        this.firstUserInterceptor = firstUserInterceptor;
        this.lastModelAndViewInterceptor = lastModelAndViewInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.firstUserInterceptor);
        registry.addInterceptor(this.lastModelAndViewInterceptor);
    }
}
