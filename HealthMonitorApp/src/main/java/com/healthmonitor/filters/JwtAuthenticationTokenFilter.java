//package com.healthmonitor.filters;
//
//import com.healthmonitor.components.JwtService;
//import com.healthmonitor.pojo.User;
//import com.healthmonitor.services.UserService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//
//public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final static String TOKEN_HEADER = "authorization";
//    @Autowired
//    private JwtService jwtService;
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String authToken = httpRequest.getHeader(TOKEN_HEADER);
//        if (jwtService.validateTokenLogin(authToken)) {
//            String username = jwtService.getUsernameFromToken(authToken);
//            User user = userService.getUserByUsername(username);
//            if (user != null) {
//                boolean enabled = true;
//                boolean accountNonExpired = true;
//                boolean credentialsNonExpired = true;
//                boolean accountNonLocked = true;
//
//                Set<GrantedAuthority> authorities = new HashSet<>();
//                authorities.add(new SimpleGrantedAuthority(user.getRoleName()));
//
//                UserDetails userDetail = new org.springframework.security.core.userdetails.User(username, user.getPassword(), enabled, accountNonExpired,
//                        credentialsNonExpired, accountNonLocked, authorities);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
//                        null, userDetail.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
