package com.kama.notes.model.vo.message;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 消息视图对象
 */
@Data
public class MessageVO {
    /**
     * 消息ID
     */
    private Integer messageId;

    /**
     * 发送者信息
     */
    private SimpleUserVO sender;

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
     * 简单用户信息
     */
    @Data
    public static class SimpleUserVO {
        private Long userId;
        private String username;
        private String avatarUrl;
    }
} 