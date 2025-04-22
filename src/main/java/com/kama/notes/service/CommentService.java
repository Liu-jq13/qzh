package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.comment.CommentQueryParams;
import com.kama.notes.model.dto.comment.CreateCommentRequest;
import com.kama.notes.model.dto.comment.UpdateCommentRequest;
import com.kama.notes.model.vo.comment.CommentVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论服务接口
 */
@Transactional
public interface CommentService {
    /**
     * 创建评论
     *
     * @param request 创建评论请求
     * @return 创建的评论ID
     */
    ApiResponse<Integer> createComment(CreateCommentRequest request);

    /**
     * 更新评论
     *
     * @param commentId 评论ID
     * @param request 更新评论请求
     * @return 空响应
     */
    ApiResponse<EmptyVO> updateComment(Integer commentId, UpdateCommentRequest request);

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     * @return 空响应
     */
    ApiResponse<EmptyVO> deleteComment(Integer commentId);

    /**
     * 获取评论列表
     *
     * @param params 查询参数
     * @return 评论列表
     */
    ApiResponse<List<CommentVO>> getComments(CommentQueryParams params);

    /**
     * 点赞评论
     *
     * @param commentId 评论ID
     * @return 空响应
     */
    ApiResponse<EmptyVO> likeComment(Integer commentId);

    /**
     * 取消点赞评论
     *
     * @param commentId 评论ID
     * @return 空响应
     */
    ApiResponse<EmptyVO> unlikeComment(Integer commentId);
} 