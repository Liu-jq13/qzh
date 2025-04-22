package com.kama.notes.model.dto.category;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryBody {

    @NotBlank(message = "name 不能为空")
    @NotNull(message = "name 不能为空")
    @Length(max = 32, min = 1, message = "name 长度在 1 - 32 之间")
    private String name;

    @NotNull(message = "parentCategoryId 不能为空")
    @Min(value = 0, message = "parentCategoryId 必须为正整数")
    private Integer parentCategoryId;
}
