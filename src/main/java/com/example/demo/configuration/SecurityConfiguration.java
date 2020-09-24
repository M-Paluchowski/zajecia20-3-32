package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/register/**").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/aukcje", true)
                    .and()
                .csrf()
                    .ignoringAntMatchers("/h2-console/**")
//                    .disable();
                    .and()
                .headers()
                    .frameOptions().disable();
//                .httpBasic();
        //formatter:on
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}user")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withUsername("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .build();
//        auth.inMemoryAuthentication()
//                .withUser(user)
//                .withUser(admin);
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role FROM user_role WHERE username=?");
    }
}
