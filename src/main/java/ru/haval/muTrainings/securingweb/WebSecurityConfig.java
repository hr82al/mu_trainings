package ru.haval.muTrainings.securingweb;

import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
        System.out.println(user.getUsername());
        return new InMemoryUserDetailsManager(user);
    }

    // @Autowired
    // public void initialize(AuthenticationManagerBuilder builder, DataSource
    // dataSource) {
    // //
    // builder.jdbcAuthentication().dataSource(dataSource).withUser("dave").password("secret").roles("USER");
    // try {
    // builder.jdbcAuthentication().dataSource(dataSource).withUser("user").password("password").roles("USER");
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // }
}
