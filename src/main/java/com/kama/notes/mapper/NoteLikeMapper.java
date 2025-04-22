package com.kama.notes.mapper;

import com.kama.notes.model.entity.NoteLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteLikeMapper {
    /**
     * 插入一个点赞记录
     *
     * @param noteLike 要插入的点赞记录对象，包含了用户ID和笔记ID等信息
     * @return 返回影响的行数，表示插入操作是否成功
     */
    int insert(NoteLike noteLike);

    /**
     * 删除一个点赞记录对象
     *
     * @param noteLike 要删除的点赞记录，通常包含用户ID和笔记ID以定位数据库中的记录
     * @return 返回影响的行数，表示删除操作是否成功
     */
    int delete(NoteLike noteLike);

    /**
     * 根据用户ID和笔记ID列表，查找用户点赞过笔记ID列表
     * 此方法用于过滤给定的笔记ID列表，仅返回该用户标记为点赞过笔记ID
     *
     * @param userId 用户ID，用于标识用户
     * @param noteIds 笔记ID列表，待过滤的笔记ID集合
     * @return 用户点赞过笔记ID列表
     */
    List<Integer> findUserLikedNoteIds(
            @Param("userId") Long userId,
            @Param("noteIds") List<Integer> noteIds
    );

    /**
     * 根据用户ID和笔记ID，查找特定的笔记点赞记录
     * 此方法用于验证用户是否点赞特定的笔记，通过用户ID和笔记ID的组合来查询
     *
     * @param userId 用户ID，用于标识用户
     * @param noteId 笔记ID，用于标识笔记
     * @return 笔记点赞记录，如果找到则返回，否则返回null
     */
    NoteLike findByUserIdAndNoteId(
            @Param("userId") Long userId,
            @Param("noteId") Integer noteId
    );
}
