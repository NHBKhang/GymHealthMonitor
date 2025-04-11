package com.healthmonitor.configs;

import com.healthmonitor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.HiddenHttpMethodFilter;
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
@Order(3)
public class SpringSecurityConfigs {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers("/").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers("/users", "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/packages", "/packages/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.POST, "/packages/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.PUT, "/packages/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.PATCH, "/packages/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers(HttpMethod.DELETE, "/packages/**").hasRole("ADMIN")
                .requestMatchers("/schedules", "/schedules/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers("/progress", "/progress/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers("/payments", "/payments/**").hasAnyRole("ADMIN")
                .requestMatchers("/subcriptions", "/subcriptions/**").hasAnyRole("ADMIN")
                .requestMatchers("/stats", "/stats/**").hasAnyRole("ADMIN", "TRAINER")
                .requestMatchers("/resources/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login", "/signup", "/error").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/ws/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
