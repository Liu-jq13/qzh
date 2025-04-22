package com.kama.notes.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * @ClassName CollectionNote
 * @Description 收藏夹-笔记关联实体类
 * @Author Tong
 * @LastChangeDate 2024-12-16 20:11
 * @Version v1.0
 */
@Data
public class CollectionNote {
    /*
     * 收藏夹ID（联合主键）
     */
    private Integer collectionId;

    /*
     * 笔记ID（联合主键）
     */
    private Integer noteId;

    /*
     * 创建时间
     */
    private Date createdAt;

    /*
     * 更新时间
     */
    private Date updatedAt;
}
