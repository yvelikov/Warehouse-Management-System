package org.softuni.wms.config;

import org.softuni.wms.interseptors.FirstUserInterceptor;
import org.softuni.wms.interseptors.FormInterceptor;
import org.softuni.wms.interseptors.LastUrlInterceptor;
import org.softuni.wms.interseptors.UserAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final FirstUserInterceptor firstUserInterceptor;
    private final LastUrlInterceptor lastUrlInterceptor;
    private final FormInterceptor formInterceptor;
    private final UserAuthenticationInterceptor userAuthenticationInterceptor;

    @Autowired
    public WebMvcConfiguration(FirstUserInterceptor firstUserInterceptor,
                               LastUrlInterceptor lastUrlInterceptor,
                               FormInterceptor formInterceptor,
                               UserAuthenticationInterceptor userAuthenticationInterceptor) {
        this.firstUserInterceptor = firstUserInterceptor;
        this.lastUrlInterceptor = lastUrlInterceptor;
        this.formInterceptor = formInterceptor;
        this.userAuthenticationInterceptor = userAuthenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.firstUserInterceptor);
        registry.addInterceptor(this.lastUrlInterceptor);
        registry.addInterceptor(this.formInterceptor).addPathPatterns("/admin/users/edit/{id}", "/admin/users/delete/{id}");
        registry.addInterceptor(this.userAuthenticationInterceptor);
    }
}
