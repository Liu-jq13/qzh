package com.kama.notes.model.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * 统计信息实体，包含登录、注册、笔记等统计数据
 */
@Data
public class Statistic {
    /**
     * 主键 ID
     */
    private Integer id;

    /**
     * 当天登录次数
     */
    private Integer loginCount;

    /**
     * 当天注册人数
     */
    private Integer registerCount;

    /**
     * 累计注册总人数
     */
    private Integer totalRegisterCount;

    /**
     * 当天笔记数量
     */
    private Integer noteCount;

    /**
     * 当天提交的笔记数量
     */
    private Integer submitNoteCount;

    /**
     * 累计笔记总数量
     */
    private Integer totalNoteCount;

    /**
     * 统计日期
     */
    private LocalDate date;
}
