package com.kama.notes.model.vo.question;

import lombok.Data;

// 用于普通用户查询携带个人信息的问题 VO
@Data
public class QuestionUserVO {
    /*
     * 问题ID（主键）
     */
    private Integer questionId;

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

    /**
     * 用户问题状态
     */
    private UserQuestionStatus userQuestionStatus;

    @Data
    public static class UserQuestionStatus {
        private boolean finished = false;  // 用户是否完成过这道题
    }
}
