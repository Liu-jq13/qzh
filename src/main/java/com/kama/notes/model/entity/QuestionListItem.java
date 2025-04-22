package com.kama.notes.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * @ClassName QuestionListItem
 * @Description 题单-题目关联实体类
 * @Author Tong
 * @LastChangeDate 2024-12-16 20:15
 * @Version v1.0
 */
@Data
public class QuestionListItem {
    /*
     * 题单ID（联合主键）
     */
    private Integer questionListId;

    /*
     * 题目ID（联合主键）
     */
    private Integer questionId;

    /*
     * 题单内题目的顺序，从1开始
     */
    private Integer rank;

    /*
     * 创建时间
     */
    private Date createdAt;

    /*
     * 更新时间
     */
    private Date updatedAt;
}
