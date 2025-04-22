package com.kama.notes.event;

import com.kama.notes.model.vo.message.MessageVO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 消息事件
 * 用于系统内部的消息传递
 */
@Getter
public class MessageEvent extends ApplicationEvent {
    
    private final MessageVO message;
    private final Long receiverId;
    private final String eventType;

    public MessageEvent(Object source, MessageVO message, Long receiverId, String eventType) {
        super(source);
        this.message = message;
        this.receiverId = receiverId;
        this.eventType = eventType;
    }

    public static MessageEvent createCommentEvent(Object source, MessageVO message, Long receiverId) {
        return new MessageEvent(source, message, receiverId, "COMMENT");
    }

    public static MessageEvent createLikeEvent(Object source, MessageVO message, Long receiverId) {
        return new MessageEvent(source, message, receiverId, "LIKE");
    }

    public static MessageEvent createSystemEvent(Object source, MessageVO message, Long receiverId) {
        return new MessageEvent(source, message, receiverId, "SYSTEM");
    }
} 