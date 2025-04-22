package com.kama.notes.model.vo.question;

import lombok.Data;

import java.time.LocalDateTime;

// 用于管理员批量查询题目
@Data
public class QuestionVO {
    private Integer questionId;
    private Integer categoryId;
    private String title;
    private Integer difficulty;
    private String examPoint;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
