package com.kama.notes.model.vo.questionListItem;

import com.kama.notes.model.vo.question.BaseQuestionVO;
import lombok.Data;

@Data
public class QuestionListItemUserVO {
    /**
     * 题单ID（联合主键）
     */
    private Integer questionListId;

    /**
     * 题目ID（联合主键）
     */
    private BaseQuestionVO question;

    /**
     * 用户是否完成了这道题
     */
    private UserQuestionStatus userQuestionStatus;

    /*
     * 题单内题目的顺序，从1开始
     */
    private Integer rank;

    @Data
    public static class UserQuestionStatus {
        private boolean finished;
    }
}
