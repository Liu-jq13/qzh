package com.kama.notes.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * @ClassName NoteLike
 * @Description 笔记点赞关联实体类
 * @Author Tong
 * @LastChangeDate 2024-12-16 20:04
 * @Version v1.0
 */
@Data
public class NoteLike {
    /*
     * 笔记ID（联合主键）
     */
    private Integer noteId;

    /*
     * 点赞用户ID（联合主键）
     */
    private Long userId;

    /*
     * 创建时间
     */
    private Date createdAt;

    /*
     * 更新时间
     */
    private Date updatedAt;
}
