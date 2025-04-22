package com.kama.notes.model.vo.question;

import lombok.Data;

@Data
public class QuestionNoteVO {
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
     * 关于这道题用户的详细信息
     */
    private UserNote userNote;

    @Data
    public static class UserNote {
        /*
         * 是否完成
         */
        private boolean finished = false;
        /**
         * noteId
         */
        private Integer noteId;
        /**
         * 笔记内容
         */
        private String content;
    }
}
