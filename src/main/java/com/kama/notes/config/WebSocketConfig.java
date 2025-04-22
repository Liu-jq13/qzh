package com.kama.notes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kama.notes.utils.JwtUtil;
import com.kama.notes.websocket.MessageWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * WebSocket配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public WebSocketConfig(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageWebSocketHandler(), "/ws/message")
                .setAllowedOrigins("*");  // 生产环境需要限制允许的域名
    }

    @Bean
    public MessageWebSocketHandler messageWebSocketHandler() {
        return new MessageWebSocketHandler(jwtUtil, objectMapper);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置消息大小限制为8KB
        container.setMaxTextMessageBufferSize(8192);
        // 设置二进制消息大小限制为8KB
        container.setMaxBinaryMessageBufferSize(8192);
        // 设置空闲超时时间为60秒
        container.setMaxSessionIdleTimeout(60000L);
        return container;
    }
} 