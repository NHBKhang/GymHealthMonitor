package com.healthmonitor.configs;

import com.healthmonitor.controllers.ws.SocketNotificationHandler;
import com.healthmonitor.controllers.ws.SocketScheduleHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfigs implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketNotificationHandler(), "/ws/notifications")
                .setAllowedOrigins("*");
        registry.addHandler(new SocketScheduleHandler(), "/ws/schedule")
                .setAllowedOrigins("*");
    }
}