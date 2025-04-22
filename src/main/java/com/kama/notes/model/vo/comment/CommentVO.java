package com.kama.notes.model.vo.comment;

import com.kama.notes.model.vo.user.UserActionVO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论视图对象
 */
@Data
public class CommentVO {
    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 笔记ID
     */
    private Integer noteId;

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

    /**
     * 作者信息
     */
    private SimpleAuthorVO author;

    /**
     * 用户操作信息
     */
    private UserActionVO userActions;

    /**
     * 回复列表
     */
    private List<CommentVO> replies;

    /**
     * 简单作者信息
     */
    @Data
    public static class SimpleAuthorVO {
        private Long userId;
        private String username;
        private String avatarUrl;
    }
} 