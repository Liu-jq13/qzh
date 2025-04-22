package com.kama.notes.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论点赞实体类
 */
@Data
public class CommentLike {
    /**
     * 评论点赞ID
     */
    private Integer commentLikeId;

    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
} 