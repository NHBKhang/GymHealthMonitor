package com.healthmonitor.configs;

import com.healthmonitor.controllers.ws.SocketNotificationHandler;
import com.healthmonitor.controllers.ws.SocketScheduleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfigs implements WebSocketConfigurer {

    @Bean
    public SocketNotificationHandler notificationHandler() {
        return new SocketNotificationHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler(), "/ws/notifications")
                .setAllowedOriginPatterns("*")
                .withSockJS();
//        registry.addHandler(new SocketScheduleHandler(), "/ws/schedule")
//                .setAllowedOrigins("*");
    }
}
