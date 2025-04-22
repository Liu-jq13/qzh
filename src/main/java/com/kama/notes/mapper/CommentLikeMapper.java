package com.kama.notes.mapper;

import com.kama.notes.model.entity.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * 评论点赞Mapper接口
 */
@Mapper
public interface CommentLikeMapper {
    /**
     * 插入评论点赞
     *
     * @param commentLike 评论点赞实体
     */
    void insert(CommentLike commentLike);

    /**
     * 删除评论点赞
     *
     * @param commentId 评论ID
     * @param userId 用户ID
     */
    void delete(@Param("commentId") Integer commentId, @Param("userId") Long userId);

    /**
     * 查询用户点赞的评论ID列表
     *
     * @param userId 用户ID
     * @param commentIds 评论ID列表
     * @return 用户点赞的评论ID集合
     */
    Set<Integer> findUserLikedCommentIds(@Param("userId") Long userId,
                                       @Param("commentIds") List<Integer> commentIds);

    @Select("SELECT COUNT(*) > 0 FROM comment_like " +
            "WHERE user_id = #{userId} AND comment_id = #{commentId}")
    Boolean checkIsLiked(@Param("userId") Long userId, @Param("commentId") Integer commentId);
} 