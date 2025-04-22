package com.kama.notes.model.dto.note;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 更新笔记请求DTO
 */
@Data
public class UpdateNoteRequest {
    /*
     * 笔记内容
     */
    @NotNull(message = "笔记内容不能为空")
    @NotBlank(message = "笔记内容不能为空")
    private String content;
} 