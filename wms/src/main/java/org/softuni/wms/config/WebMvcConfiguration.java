package org.softuni.wms.config;

import org.softuni.wms.interseptors.FirstUserInterceptor;
import org.softuni.wms.interseptors.FormInterceptor;
import org.softuni.wms.interseptors.LastUrlInterceptor;
import org.softuni.wms.interseptors.UserAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
        registry.addInterceptor(this.formInterceptor)
                .addPathPatterns("/admin/users/edit/{id}",
                        "/admin/users/disable/{id}",
                        "/admin/users/enable/{id}",
                        "/documents/delivery_notes",
                        "/documents/issue_notes",
                        "/documents/delivery_notes/search",
                        "/documents/issue_notes/search",
                        "/documents/delivery_notes/details/*",
                        "/documents/issue_notes/details/*");
        registry.addInterceptor(this.userAuthenticationInterceptor);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/notFound").setStatusCode(HttpStatus.NOT_FOUND).setViewName("errors/404.html");
//        registry.setOrder(100000);
//    }
//
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
//        return container -> {
//            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
//                    "/notFound"));
//        };
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        resolvers.add(resolver);
    }
}
