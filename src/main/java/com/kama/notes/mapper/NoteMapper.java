package com.kama.notes.mapper;

import com.kama.notes.model.dto.note.NoteQueryParams;
import com.kama.notes.model.entity.Note;
import com.kama.notes.model.vo.note.NoteHeatMapItem;
import com.kama.notes.model.vo.note.NoteRankListItem;
import com.kama.notes.model.vo.note.Top3Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface NoteMapper {
    /**
     * 查询笔记的总数
     *
     * @param params 查询参数，用于过滤笔记
     * @return 笔记的总数量
     */
    int countNotes(@Param("params") NoteQueryParams params);

    /**
     * 根据笔记ID查询笔记
     *
     * @param noteId 笔记ID，用于定位特定笔记
     * @return 返回对应的笔记对象，如果找不到则返回 null
     */
    Note findById(@Param("noteId") Integer noteId);

    /**
     * 根据查询参数获取笔记列表
     *
     * @param params 查询参数，用于过滤笔记
     * @param offset 偏移量，用于分页
     * @param limit  每页大小，用于分页
     * @return 笔记列表，返回符合查询条件的笔记
     */
    List<Note> findByQueryParams(@Param("params") NoteQueryParams params,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);

    /**
     * 根据用户ID和问题ID查询笔记
     *
     * @param authorId   用户ID，用于标识特定用户
     * @param questionId 问题ID，用于标识特定问题
     * @return 返回匹配的笔记对象，如果找不到匹配的笔记，则返回 null
     */
    Note findByAuthorIdAndQuestionId(@Param("authorId") Long authorId,
                                     @Param("questionId") Integer questionId);

    /**
     * 根据用户ID查询笔记列表
     * @param authorId 用户ID
     * @return 用户创建的笔记列表
     */
    List<Note> findByAuthorId(@Param("authorId") Long authorId);


    /**
     * 根据用户ID和问题ID列表，过滤出用户已完成的问题ID列表
     *
     * @param authorId    用户ID，用于标识特定用户
     * @param questionIds 问题ID列表，表示待查询的问题范围
     * @return 用户已完成的问题ID列表，如果用户未完成任何问题，则返回空集合
     */
    Set<Integer> filterFinishedQuestionIdsByUser(@Param("authorId") Long authorId,
                                                 @Param("questionIds") List<Integer> questionIds);

    /**
     * 插入一条新的笔记
     *
     * @param note 笔记对象，包含要插入的笔记信息
     * @return 插入成功
     */
    int insert(Note note);

    /**
     * 更新笔记信息
     *
     * @param note 笔记对象，包含要更新的笔记信息
     * @return 更新成功记录数
     */
    int update(Note note);

    /**
     * 点赞笔记
     *
     * @param noteId 笔记ID，用于标识要点赞的笔记
     * @return 点赞成功记录数
     */
    int likeNote(@Param("noteId") Integer noteId);

    /**
     * 取消点赞笔记
     *
     * @param noteId 笔记ID，用于标识要取消点赞的笔记
     * @return 取消点赞成功记录数
     */
    int unlikeNote(@Param("noteId") Integer noteId);

    /**
     * 收藏笔记
     *
     * @param noteId 笔记ID，用于标识要收藏的笔记
     * @return 收藏结果
     */
    int collectNote(@Param("noteId") Integer noteId);

    /**
     * 取消收藏笔记
     *
     * @param noteId 笔记ID，用于标识要取消收藏的笔记
     * @return 取消收藏结果
     */
    int unCollectNote(@Param("noteId") Integer noteId);

    /**
     * 根据笔记ID删除笔记
     *
     * @param noteId 笔记ID，用于标识要删除的笔记
     * @return 删除成功记录数
     */
    int deleteById(@Param("noteId") Integer noteId);

    /**
     * 每日笔记提交数排行榜
     *
     * @return 排行榜数组
     */
    List<NoteRankListItem> submitNoteRank();

    /**
     * 提交热力图
     *
     * @return 用户提交热力图信息
     */
    List<NoteHeatMapItem> submitNoteHeatMap(@Param("authorId") Long authorId);

    /**
     * 用户提交 top3Count
     *
     * @return 用户提交 top3Count
     */
    Top3Count submitNoteTop3Count(@Param("authorId") Long authorId);

    /**
     * 当日笔记数
     *
     * @return 当日笔记数
     */
    int getTodayNoteCount();

    /**
     * 当日提交笔记人数
     * @return 当日提交笔记人数
     */
    int getTodaySubmitNoteUserCount();

    /**
     * 笔记总数
     * @return 笔记总数
     */
    int getTotalNoteCount();

    /**
     * 增加笔记评论数
     *
     * @param noteId 笔记ID
     */
    void incrementCommentCount(@Param("noteId") Integer noteId);

    /**
     * 减少笔记评论数
     *
     * @param noteId 笔记ID
     */
    void decrementCommentCount(@Param("noteId") Integer noteId);

    /**
     * 搜索笔记
     *
     * @param keyword 关键词
     * @param limit 限制数量
     * @param offset 偏移量
     * @return 笔记列表
     */
    List<Note> searchNotes(@Param("keyword") String keyword,
                          @Param("limit") int limit,
                          @Param("offset") int offset);

    /**
     * 根据标签搜索笔记
     *
     * @param keyword 关键词
     * @param tag 标签
     * @param limit 限制数量
     * @param offset 偏移量
     * @return 笔记列表
     */
    List<Note> searchNotesByTag(@Param("keyword") String keyword,
                               @Param("tag") String tag,
                               @Param("limit") int limit,
                               @Param("offset") int offset);
}
