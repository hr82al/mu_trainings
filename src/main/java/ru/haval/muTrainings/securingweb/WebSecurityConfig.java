package ru.haval.muTrainings.securingweb;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.haval.muTrainings.authentication.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable();
        // http.authorizeRequests()
        // .antMatchers("/", "/images/haval_logo.jpg", "/styles/select2.min.css",
        // "/styles/bootstrap.min.css",
        // "/libs/jquery-3.5.1.min.js", "/libs/select2.min.js",
        // "/libs/bootstrap.bundle.min.js",
        // "/libs/jquery.editable.min.js", "/styles/bootstrap.min.css.map",
        // "/styles/styles.css")
        // .permitAll().anyRequest().authenticated().and().formLogin().and().logout().permitAll();
        http.authorizeRequests()
                .antMatchers("/muTrainings", "/trainings", "/positions", "/departments", "/trainingsNamesList",
                        "/positionsTrainings", "/positionsTrainings", "/employee/**", "/reports/**")
                .hasAnyAuthority("Administrator", "Engeneer", "Team Lead").antMatchers("/action_plan/**")
                .authenticated()
                .antMatchers("/", "/images/haval_logo.jpg", "/styles/select2.min.css", "/styles/bootstrap.min.css",
                        "/libs/jquery-3.5.1.min.js", "/libs/select2.min.js", "/libs/bootstrap.bundle.min.js",
                        "/styles/styles.css", "/favicon.ico")
                .permitAll().and().formLogin().loginPage("/login").and().logout().permitAll();
        /*
         * .antMatchers("/", "/images/haval_logo.jpg", "/styles/select2.min.css",
         * "/styles/bootstrap.min.css", "/libs/jquery-3.5.1.min.js",
         * "/libs/select2.min.js", "/libs/bootstrap.bundle.min.js",
         * "/libs/jquery.editable.min.js", "/styles/bootstrap.min.css.map",
         * "/styles/styles.css").permitAll() . hasAnyAuthority()
         */
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }
}
