package com.kama.notes.model.dto.questionListItem;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateQuestionListItemBody {
    @NotNull(message = "questionListId 不能为空")
    @Min(value = 1, message = "questionListId 必须为正整数")
    private Integer questionListId;

    @NotNull(message = "questionId 不能为空")
    @Min(value = 1, message = "questionId 必须为正整数")
    private Integer questionId;
}
