package com.kama.notes.model.vo.questionListItem;

import com.kama.notes.model.vo.question.BaseQuestionVO;
import lombok.Data;

@Data
public class QuestionListItemVO {
    /*
     * 题单ID（联合主键）
     */
    private Integer questionListId;

    /*
     * 题目ID（联合主键）
     */
    private BaseQuestionVO question;

    /*
     * 题单内题目的顺序，从1开始
     */
    private Integer rank;
}
