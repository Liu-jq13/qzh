package com.kama.notes.model.dto.questionList;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class UpdateQuestionListBody {
    /*
     * 题单名称
     */
    @Length(max = 32, message = "name 长度不能超过 32")
    private String name;

    /**
     * 题单类型
     */
    @Range(min = 1, max = 2, message = "type 必须为 1 或 2")
    private Integer type;

    /*
     * 题单描述
     */
    @Length(max = 255, message = "description 长度不能超过 255")
    private String description;
}
