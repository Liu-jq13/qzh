package com.kama.notes.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息实体类
 */
@Data
public class Message {
    /**
     * 消息ID
     */
    private Integer messageId;

    /**
     * 接收者ID
     */
    private Long receiverId;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 消息类型
     */
    private String type;

    /**
     * 目标ID
     */
    private Integer targetId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 是否已读
     */
    private Boolean isRead;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 