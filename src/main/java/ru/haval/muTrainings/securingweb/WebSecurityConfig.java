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
        /*
         * http .authorizeRequests() .antMatchers("/mu_trainings").permitAll()
         * .anyRequest().authenticated() .and() .formLogin() .and() .logout()
         * .permitAll();
         */
        http.authorizeRequests()
                .antMatchers("/", "/images/haval_logo.jpg", "/styles/select2.min.css", "/styles/bootstrap.min.css",
                        "/libs/jquery-3.5.1.min.js", "/libs/select2.min.js", "/libs/bootstrap.bundle.min.js",
                        "/libs/jquery.editable.min.js", "/styles/bootstrap.min.css.map", "/styles/styles.css")
                .permitAll().anyRequest().authenticated().and().formLogin().and().logout().permitAll();
    }

    // @Bean
    // @Override
    // public UserDetailsService userDetailsServiceBean() throws Exception {
    // UserDetails user =
    // User.withDefaultPasswordEncoder().username("admin").password("Haval_123").roles("USER")
    // .build();
    // System.out.println(user.getUsername());
    // return new InMemoryUserDetailsManager(user);
    // }

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
