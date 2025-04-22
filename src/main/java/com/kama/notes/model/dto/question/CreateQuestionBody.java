package com.kama.notes.model.dto.question;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateQuestionBody {
    @NotNull(message = "categoryId 不能为空")
    @Min(value = 1, message = "categoryId 必须为正整数")
    private Integer categoryId;

    @NotNull(message = "title 不能为空")
    @NotBlank(message = "title 不能为空")
    @Length(max = 255, message = "title 长度不能超过 255")
    private String title;

    @NotNull(message = "difficulty 不能为空")
    @Range(min = 1, max = 3, message = "difficulty 必须为 1, 2, 3")
    private Integer difficulty;

    @Length(max = 255, message = "examPoint 长度不能超过 255")
    private String examPoint;
}
