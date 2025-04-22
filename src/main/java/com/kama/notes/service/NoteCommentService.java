package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.entity.NoteComment;

import java.util.List;

public interface NoteCommentService {
    
    /**
     * 创建评论
     */
    ApiResponse<EmptyVO> createComment(Integer noteId, String content);

    /**
     * 删除评论
     */
    ApiResponse<EmptyVO> deleteComment(Integer commentId);

    /**
     * 获取笔记的评论列表
     */
    ApiResponse<List<NoteComment>> getComments(Integer noteId);
} 