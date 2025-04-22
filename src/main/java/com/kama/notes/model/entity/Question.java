package com.kama.notes.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @ClassName Question
 * @Description 问题实体类
 * @Author Tong
 * @LastChangeDate 2024-12-16 19:56
 * @Version v1.0
 */
@Data
public class Question {
    /*
     * 问题ID（主键）
     */
    private Integer questionId;

    /*
     * 问题所属分类ID
     */
    private Integer categoryId;

    /*
     * 问题标题
     */
    private String title;

    /*
     * 问题难度
     * 1=简单，2=中等，3=困难
     */
    private Integer difficulty;

    /*
     * 题目考点
     */
    private String examPoint;

    /*
     * 浏览量
     */
    private Integer viewCount;

    /*
     * 创建时间
     */
    private LocalDateTime createdAt;

    /*
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
