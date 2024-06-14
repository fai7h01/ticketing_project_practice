package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler successHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler successHandler) {
        this.securityService = securityService;
        this.successHandler = successHandler;
    }

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
//                        .defaultSuccessUrl("/welcome")
                        .successHandler(successHandler)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"))
                .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(1200)
                        .key("ticketing")
                        .userDetailsService(securityService))
                .build();

    }
}