package com.kama.notes.model.dto.question;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class QuestionQueryParam {

    @Min(value = 1, message = "categoryId 必须为正整数")
    private Integer categoryId;

    @Pattern(regexp = "^(view|difficulty)$", message = "sort 必须为 view 或 difficulty")
    private String sort;

    @Pattern(regexp = "^(asc|desc)$", message = "order 必须为 asc 或 desc")
    private String order;

    @NotNull(message = "page 不能为空")
    @Min(value = 1, message = "page 必须为正整数")
    private Integer page;

    @NotNull(message = "pageSize 不能为空")
    @Min(value = 1, message = "pageSize 必须为正整数")
    @Max(value = 200, message = "pageSize 不能超过 200")
    private Integer pageSize;
}
