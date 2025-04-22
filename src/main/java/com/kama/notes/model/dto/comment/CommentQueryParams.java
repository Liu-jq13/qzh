package com.kama.notes.model.dto.comment;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 评论查询参数
 */
@Data
public class CommentQueryParams {
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
     * 作者ID
     */
    private Long authorId;

    /**
     * 页码
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Integer page;

    /**
     * 每页大小
     */
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于0")
    private Integer pageSize;
} 