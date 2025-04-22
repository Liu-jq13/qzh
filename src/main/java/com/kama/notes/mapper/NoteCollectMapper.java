package com.kama.notes.mapper;

import com.kama.notes.model.entity.NoteCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 笔记收藏Mapper接口
 */
@Mapper
public interface NoteCollectMapper {
    /**
     * 插入收藏记录
     *
     * @param noteCollect 收藏记录
     * @return 影响的行数
     */
    int insert(NoteCollect noteCollect);

    /**
     * 删除收藏记录
     *
     * @param noteId 笔记ID
     * @param userId 用户ID
     * @return 影响的行数
     */
    int delete(@Param("noteId") Integer noteId, @Param("userId") Long userId);

    /**
     * 查找收藏记录
     *
     * @param noteId 笔记ID
     * @param userId 用户ID
     * @return 收藏记录
     */
    NoteCollect findByNoteIdAndUserId(@Param("noteId") Integer noteId, @Param("userId") Long userId);

    /**
     * 获取用户收藏的笔记ID列表
     *
     * @param userId 用户ID
     * @return 笔记ID列表
     */
    List<Integer> findNoteIdsByUserId(@Param("userId") Long userId);
} 