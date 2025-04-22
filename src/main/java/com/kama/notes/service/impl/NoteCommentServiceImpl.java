package com.kama.notes.service.impl;

import com.kama.notes.annotation.NeedLogin;
import com.kama.notes.mapper.NoteCommentMapper;
import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.entity.Note;
import com.kama.notes.model.entity.NoteComment;
import com.kama.notes.scope.RequestScopeData;
import com.kama.notes.service.MessageService;
import com.kama.notes.service.NoteCommentService;
import com.kama.notes.utils.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteCommentServiceImpl implements NoteCommentService {

    private final NoteCommentMapper noteCommentMapper;
    private final NoteMapper noteMapper;
    private final RequestScopeData requestScopeData;
    private final MessageService messageService;

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<EmptyVO> createComment(Integer noteId, String content) {
        Long userId = requestScopeData.getUserId();

        // 查询笔记
        Note note = noteMapper.findById(noteId);
        if (note == null) {
            return ApiResponseUtil.error("笔记不存在");
        }

        try {
            // 创建评论
            NoteComment comment = new NoteComment();
            comment.setNoteId(noteId);
            comment.setUserId(userId);
            comment.setContent(content);
            comment.setCreatedAt(new Date());
            comment.setUpdatedAt(new Date());
            comment.setIsDeleted(false);
            noteCommentMapper.insert(comment);

            // 发送评论通知
            messageService.createMessage(
                note.getAuthorId(),
                userId,
                "COMMENT",
                noteId,
                "评论了你的笔记"
            );

            return ApiResponseUtil.success("评论成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("评论失败");
        }
    }

    @Override
    @NeedLogin
    public ApiResponse<EmptyVO> deleteComment(Integer commentId) {
        Long userId = requestScopeData.getUserId();

        // 查询评论
        NoteComment comment = noteCommentMapper.findById(commentId);
        if (comment == null || !comment.getUserId().equals(userId)) {
            return ApiResponseUtil.error("无权删除该评论");
        }

        try {
            // 软删除评论
            comment.setIsDeleted(true);
            comment.setUpdatedAt(new Date());
            noteCommentMapper.update(comment);
            return ApiResponseUtil.success("删除成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("删除失败");
        }
    }

    @Override
    public ApiResponse<List<NoteComment>> getComments(Integer noteId) {
        try {
            List<NoteComment> comments = noteCommentMapper.findByNoteId(noteId);
            return ApiResponseUtil.success("获取评论成功", comments);
        } catch (Exception e) {
            return ApiResponseUtil.error("获取评论失败");
        }
    }
} 