package com.kama.notes.model.dto.note;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 笔记查询参数DTO
 */
@Data
public class NoteQueryParams {
    /*
     * 问题ID
     * 必须是正整数
     */
    @Min(value = 1, message = "问题ID必须是正整数")
    private Integer questionId;

    /*
     * 作者ID
     * 必须是正整数且符合系统生成的范围
     */
    @Min(value = 1, message = "作者ID必须是正整数")
    private Long authorId;

    /*
     * 收藏夹ID
     * 必须是正整数
     */
    @Min(value = 1, message = "收藏夹ID必须是正整数")
    private Integer collectionId;

    /*
     * 排序字段
     * 只能是固定的枚举值（比如 "create", "update"）。
     */
    @Pattern(
            regexp = "create",
            message = "create"
    )
    private String sort;

    /*
     * 排序方向
     * 只能是 "asc" 或 "desc"，区分大小写。
     */
    @Pattern(
            regexp = "asc|desc",
            message = "排序方向必须是 asc 或 desc"
    )
    private String order;

    /*
     * 最近天数
     * 必须是1到365之间的整数，默认限制为一年内。
     */
    @Min(value = 1, message = "最近天数必须至少为1天")
    @Max(value = 365, message = "最近天数不能超过365天")
    private Integer recentDays;

    /*
     * 当前页码
     * 必须是正整数，默认为1。
     */
    @NotNull(message = "当前页码不能为空")
    @Min(value = 1, message = "当前页码必须大于等于1")
    @Max(value = 10000, message = "当前页码不能超过10,000")
    private Integer page = 1;

    /*
     * 每页大小
     * 必须是正整数，限制范围在 1到100之间。
     */
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于等于1")
    @Max(value = 200, message = "每页大小不能超过100")
    private Integer pageSize = 10;
}
