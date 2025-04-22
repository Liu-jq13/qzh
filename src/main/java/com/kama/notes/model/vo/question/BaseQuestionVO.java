package com.kama.notes.model.vo.question;

import lombok.Data;

@Data
public class BaseQuestionVO {
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
}
