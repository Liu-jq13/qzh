package com.kama.notes.model.dto.collection;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateCollectionBody {
    @NotNull(message = "name 不能为空")
    @NotBlank(message = "name 不能为空")
    private String name;
    private String description;
}
