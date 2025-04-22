package com.kama.notes.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 收藏夹实体类
 */
@Data
public class Collection {
    /*
     * 收藏夹ID（主键）
     */
    private Integer collectionId;

    /*
     * 收藏夹名称
     */
    private String name;

    /*
     * 收藏夹描述
     */
    private String description;

    /*
     * 收藏夹创建者ID
     */
    private Long creatorId;

    /*
     * 创建时间
     */
    private Date createdAt;

    /*
     * 更新时间
     */
    private Date updatedAt;
} 