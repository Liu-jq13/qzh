package com.kama.notes.mapper;

import com.kama.notes.model.dto.comment.CommentQueryParams;
import com.kama.notes.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper接口
 */
@Mapper
public interface CommentMapper {
    /**
     * 插入评论
     *
     * @param comment 评论实体
     */
    void insert(Comment comment);

    /**
     * 更新评论
     *
     * @param comment 评论实体
     */
    void update(Comment comment);

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     */
    void deleteById(Integer commentId);

    /**
     * 根据ID查询评论
     *
     * @param commentId 评论ID
     * @return 评论实体
     */
    Comment findById(Integer commentId);

    /**
     * 查询评论列表
     *
     * @param params 查询参数
     * @param pageSize 每页大小
     * @param offset 偏移量
     * @return 评论列表
     */
    List<Comment> findByQueryParam(@Param("params") CommentQueryParams params,
                                 @Param("pageSize") Integer pageSize,
                                 @Param("offset") Integer offset);

    /**
     * 统计评论数量
     *
     * @param params 查询参数
     * @return 评论数量
     */
    int countByQueryParam(@Param("params") CommentQueryParams params);

    /**
     * 增加评论点赞数
     *
     * @param commentId 评论ID
     */
    void incrementLikeCount(Integer commentId);

    /**
     * 减少评论点赞数
     *
     * @param commentId 评论ID
     */
    void decrementLikeCount(Integer commentId);

    /**
     * 增加评论回复数
     *
     * @param commentId 评论ID
     */
    void incrementReplyCount(Integer commentId);

    /**
     * 减少评论回复数
     *
     * @param commentId 评论ID
     */
    void decrementReplyCount(Integer commentId);
} 