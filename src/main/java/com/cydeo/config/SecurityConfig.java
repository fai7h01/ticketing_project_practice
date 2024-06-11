package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeRequest -> {
                    authorizeRequest.requestMatchers("/user/**").hasAuthority("Admin")
                            .requestMatchers("/project/**").hasAuthority("Manager")
                            .requestMatchers("/task/employee/**").hasAuthority("Employee")
                            .requestMatchers("/task/**").hasAuthority("Manager")
                            .requestMatchers("/", "/login", "/fragments/**", "/assets/**", "/images/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .defaultSuccessUrl("/welcome")
                        .failureUrl("/login?error=true")
                        .permitAll())
                .build();

    }
}