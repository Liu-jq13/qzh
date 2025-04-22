package com.kama.notes.model.dto.collection;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CollectionQueryParams {
    @NotNull(message = "creatorId 不能为空")
    @Min(value = 1, message = "creatorId 必须为正整数")
    private Long creatorId;

    @Min(value = 1, message = "noteId 必须为正整数")
    private Integer noteId;
}
