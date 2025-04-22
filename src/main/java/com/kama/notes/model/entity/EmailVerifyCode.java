package com.kama.notes.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmailVerifyCode {
    private Long id;
    private String email;
    private String code;
    private String type;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private Boolean used;
} 