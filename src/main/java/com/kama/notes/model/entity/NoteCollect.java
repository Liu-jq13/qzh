package com.kama.notes.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 笔记收藏实体类
 */
@Data
public class NoteCollect {
    /**
     * 收藏ID
     */
    private Integer collectId;

    /**
     * 笔记ID
     */
    private Integer noteId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 