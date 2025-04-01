//package com.healthmonitor.configs;
//
//import com.healthmonitor.filters.CustomAccessDeniedHandler;
//import com.healthmonitor.filters.JwtAuthenticationTokenFilter;
//import com.healthmonitor.filters.RestAuthenticationEntryPoint;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableWebSecurity
//@EnableTransactionManagement
//@ComponentScan(basePackages = {
//    "com.healthmonitor.controllers",
//    "com.healthmonitor.repositories",
//    "com.healthmonitor.services",
////    "com.healthmonitor.components"
//})
//@Order(1)
//class JwtSecurityConfigs extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
//        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
//        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
//        return jwtAuthenticationTokenFilter;
//    }
//
//    @Bean
//    public RestAuthenticationEntryPoint restServicesEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public CustomAccessDeniedHandler customAccessDeniedHandler() {
//        return new CustomAccessDeniedHandler();
//    }
//
//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/login/**").permitAll()
//                .antMatchers("/api/users/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "MEMBER")
//                .antMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN", "MEMBER")
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN", "MEMBER")
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(restServicesEntryPoint())
//                .accessDeniedHandler(customAccessDeniedHandler());
////                .and()
////                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
