package com.kama.notes.listener;

import com.kama.notes.event.MessageEvent;
import com.kama.notes.service.MessageService;
import com.kama.notes.websocket.MessageWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息事件监听器
 * 用于处理系统内的消息事件
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final MessageService messageService;
    private final MessageWebSocketHandler messageWebSocketHandler;

    /**
     * 异步处理消息事件
     */
    @Async
    @EventListener
    public void handleMessageEvent(MessageEvent event) {
        try {
            log.info("收到{}类型的消息事件, 接收者: {}", event.getEventType(), event.getReceiverId());
            
            // 1. 保存消息到数据库
            messageService.createMessage(
                event.getReceiverId(),
                event.getMessage().getSender().getUserId(),
                event.getEventType(),
                event.getMessage().getTargetId(),
                event.getMessage().getContent()
            );

            // 2. 如果用户在线，通过WebSocket推送消息
            if (messageWebSocketHandler.isUserOnline(event.getReceiverId())) {
                messageWebSocketHandler.sendMessageToUser(event.getReceiverId(), event.getMessage());
            }

        } catch (Exception e) {
            log.error("处理消息事件时发生错误", e);
            // 这里可以添加重试逻辑或者将失败的消息记录到特定的队列中
        }
    }
} 