package com.healthmonitor.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.healthmonitor.controllers",
    "com.healthmonitor.repositories",
    "com.healthmonitor.services",
    "com.healthmonitor.components"
})
public class SpringSecurityConfigs {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/").hasAnyRole("TRAINER", "ADMIN")
                    .requestMatchers("/users").hasAnyRole("ADMIN")
                    .requestMatchers("/api/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/packages/**").hasAnyRole("TRAINER", "ADMIN")
                    .requestMatchers("/resources/**", "/css/**", "/js/**", "/images/**").permitAll()
                    .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login?error=true").permitAll())
                .logout(logout -> logout
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID").permitAll());

        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
