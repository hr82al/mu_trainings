package ru.haval.muTrainings.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable();
        /*
         * http .authorizeRequests() .antMatchers("/mu_trainings").permitAll()
         * .anyRequest().authenticated() .and() .formLogin() .and() .logout()
         * .permitAll();
         */
        http.authorizeRequests()
                .antMatchers("/", "/images/haval_logo.jpg", "/styles/select2.min.css", "/styles/bootstrap.min.css",
                        "/styles/styles.css")
                .permitAll().anyRequest().authenticated().and().formLogin().and().logout().permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
