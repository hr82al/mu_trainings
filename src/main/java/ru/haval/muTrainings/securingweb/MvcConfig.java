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
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/trainingsNames").setViewName("trainingsNames");
        registry.addViewController("/action_plan").setViewName("action_plan");
        registry.addViewController("/logout").setViewName("logout");
    }
}
