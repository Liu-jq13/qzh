package com.kama.notes.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
public class Comment {
    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 笔记ID
     */
    private Integer noteId;

    /**
     * 作者ID
     */
    private Long authorId;

    /**
     * 父评论ID
     */
    private Integer parentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 回复数
     */
    private Integer replyCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
} 