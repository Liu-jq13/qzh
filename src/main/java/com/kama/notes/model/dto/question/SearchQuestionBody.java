package com.kama.notes.model.dto.question;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SearchQuestionBody {
    @NotNull(message = "keyword 不能为空")
    @NotEmpty(message = "keyword 不能为空")
    @Length(min = 1, max = 32, message = "keyword 长度在 1 和 32 范围内")
    private String keyword;
    // TODO: 后续需要完善，添加分页功能
}
