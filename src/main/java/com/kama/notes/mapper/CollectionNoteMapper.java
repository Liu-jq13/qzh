package com.kama.notes.mapper;

import com.kama.notes.model.entity.CollectionNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface CollectionNoteMapper {
    /**
     * 查询用户收藏的笔记 ID 列表
     *
     * @param userId  用户 ID
     * @param noteIds 笔记 ID 列表
     * @return 用户收藏的笔记 ID 列表
     */
    List<Integer> findUserCollectedNoteIds(
            @Param("userId") Long userId,
            @Param("noteIds") List<Integer> noteIds
    );

    /**
     * 筛选出所给的收藏夹 ID 列表中，收藏了 noteId 对应的 note 的记录
     *
     * @param noteId        笔记 ID
     * @param collectionIds 收藏夹 ID 列表
     * @return 筛选结果
     */
    Set<Integer> filterCollectionIdsByNoteId(
            @Param("noteId") Integer noteId,
            @Param("collectionIds") List<Integer> collectionIds);

    /**
     * 插入记录
     *
     * @param collectionNote 收藏笔记记录
     * @return 插入记录数
     */
    int insert(CollectionNote collectionNote);

    /**
     * 根据 collectionId 删除记录
     *
     * @param collectionId 收藏夹 ID
     * @return 删除记录数
     */
    int deleteByCollectionId(@Param("collectionId") Integer collectionId);

    /**
     * 根据 collectionId 和 noteId 删除记录
     *
     * @param collectionId 收藏夹 ID
     * @param noteId       笔记 ID
     * @return 删除记录数
     */
    int deleteByCollectionIdAndNoteId(
            @Param("collectionId") Integer collectionId,
            @Param("noteId") Integer noteId);
}
