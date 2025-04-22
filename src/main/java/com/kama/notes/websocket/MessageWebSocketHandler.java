package com.kama.notes.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kama.notes.model.vo.message.MessageVO;
import com.kama.notes.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket消息处理器
 */
@Slf4j
@RequiredArgsConstructor
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    // 用户ID -> WebSocket会话的映射
    private static final Map<Long, WebSocketSession> USER_SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            log.info("用户[{}]建立WebSocket连接", userId);
            USER_SESSIONS.put(userId, session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 处理接收到的消息
        try {
            String payload = message.getPayload();
            log.debug("收到消息: {}", payload);
            // 这里可以添加消息处理逻辑
        } catch (Exception e) {
            log.error("处理WebSocket消息时发生错误", e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            log.info("用户[{}]断开WebSocket连接", userId);
            USER_SESSIONS.remove(userId);
        }
    }

    /**
     * 发送消息给指定用户
     */
    public void sendMessageToUser(Long userId, MessageVO message) {
        WebSocketSession session = USER_SESSIONS.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String messageText = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(messageText));
                log.debug("发送消息到用户[{}]: {}", userId, messageText);
            } catch (Exception e) {
                log.error("发送消息给用户[{}]时发生错误", userId, e);
            }
        }
    }

    /**
     * 从WebSocket会话中获取用户ID
     */
    private Long getUserIdFromSession(WebSocketSession session) {
        String token = session.getHandshakeHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.getUserIdFromToken(token);
        }
        return null;
    }

    /**
     * 获取当前在线用户数
     */
    public int getOnlineUserCount() {
        return USER_SESSIONS.size();
    }

    /**
     * 判断用户是否在线
     */
    public boolean isUserOnline(Long userId) {
        WebSocketSession session = USER_SESSIONS.get(userId);
        return session != null && session.isOpen();
    }
} 