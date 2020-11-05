package ru.haval.muTrainings.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/mu_trainings").setViewName("muTrainings");
        registry.addViewController("/positions").setViewName("positions");
        registry.addViewController("/").setViewName("muTrainings");
        registry.addViewController("/login").setViewName("login");
    }
}
