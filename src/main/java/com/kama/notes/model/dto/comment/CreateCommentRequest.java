package com.kama.notes.model.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 创建评论请求
 */
@Data
public class CreateCommentRequest {
    /**
     * 笔记ID
     */
    @NotNull(message = "笔记ID不能为空")
    private Integer noteId;

    /**
     * 父评论ID
     */
    private Integer parentId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;
} 