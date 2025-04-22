package com.kama.notes.model.dto.notification;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class NotificationDTO {
    @NotEmpty(message = "content 不能为空")
    @NotNull(message = "content 不能为空")
    private String content;
}
