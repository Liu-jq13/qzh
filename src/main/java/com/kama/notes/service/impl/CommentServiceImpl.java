package com.kama.notes.service.impl;

import com.kama.notes.annotation.NeedLogin;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.mapper.CommentMapper;
import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.mapper.UserMapper;
import com.kama.notes.mapper.CommentLikeMapper;
import com.kama.notes.model.entity.Comment;
import com.kama.notes.model.entity.Note;
import com.kama.notes.model.entity.User;
import com.kama.notes.model.dto.comment.CommentQueryParams;
import com.kama.notes.model.dto.comment.CreateCommentRequest;
import com.kama.notes.model.dto.comment.UpdateCommentRequest;
import com.kama.notes.model.vo.comment.CommentVO;
import com.kama.notes.model.vo.user.UserVO;
import com.kama.notes.model.vo.user.UserActionVO;
import com.kama.notes.scope.RequestScopeData;
import com.kama.notes.service.CommentService;
import com.kama.notes.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;
    private final CommentLikeMapper commentLikeMapper;
    private final MessageService messageService;
    private final RequestScopeData requestScopeData;

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<Integer> createComment(CreateCommentRequest request) {
        log.info("开始创建评论: request={}", request);
        
        try {
            Long userId = requestScopeData.getUserId();
            
            // 获取笔记信息
            Note note = noteMapper.findById(request.getNoteId());
            if (note == null) {
                log.error("笔记不存在: noteId={}", request.getNoteId());
                return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "笔记不存在");
            }
            
            // 创建评论
            Comment comment = new Comment();
            comment.setNoteId(request.getNoteId());
            comment.setContent(request.getContent());
            comment.setAuthorId(userId);
            comment.setParentId(request.getParentId());
            comment.setLikeCount(0);
            comment.setReplyCount(0);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUpdatedAt(LocalDateTime.now());
            
            commentMapper.insert(comment);
            log.info("评论创建结果: commentId={}", comment.getCommentId());
            
            // 增加笔记评论数
            noteMapper.incrementCommentCount(request.getNoteId());
            
            // 如果是回复评论，增加父评论的回复数
            if (request.getParentId() != null) {
                commentMapper.incrementReplyCount(request.getParentId());
            }
            
            // 创建消息通知
            ApiResponse<Integer> messageResponse = messageService.createMessage(
                note.getAuthorId(),  // 接收者是笔记作者
                userId,              // 发送者是评论作者
                "COMMENT",           // 消息类型是评论
                comment.getCommentId(), // 目标ID是评论ID
                "评论了你的笔记"      // 消息内容
            );
            log.info("消息通知已创建: {}", messageResponse);
            
            return ApiResponse.success(comment.getCommentId());
        } catch (Exception e) {
            log.error("创建评论失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "创建评论失败: " + e.getMessage());
        }
    }

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<EmptyVO> updateComment(Integer commentId, UpdateCommentRequest request) {
        Long userId = requestScopeData.getUserId();

        // 查询评论
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "评论不存在");
        }

        // 检查权限
        if (!comment.getAuthorId().equals(userId)) {
            return ApiResponse.error(HttpStatus.FORBIDDEN.value(), "无权修改该评论");
        }

        try {
            // 更新评论
            comment.setContent(request.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            commentMapper.update(comment);
            return ApiResponse.success(new EmptyVO());
        } catch (Exception e) {
            log.error("更新评论失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "更新评论失败");
        }
    }

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<EmptyVO> deleteComment(Integer commentId) {
        Long userId = requestScopeData.getUserId();

        // 查询评论
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "评论不存在");
        }

        // 检查权限
        if (!comment.getAuthorId().equals(userId)) {
            return ApiResponse.error(HttpStatus.FORBIDDEN.value(), "无权删除该评论");
        }

        try {
            // 删除评论
            commentMapper.deleteById(commentId);
            return ApiResponse.success(new EmptyVO());
        } catch (Exception e) {
            log.error("删除评论失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "删除评论失败");
        }
    }

    @Override
    public ApiResponse<List<CommentVO>> getComments(CommentQueryParams params) {
        try {
            List<Comment> comments = commentMapper.findByQueryParam(params, params.getPageSize(), (params.getPage() - 1) * params.getPageSize());
            if (comments == null || comments.isEmpty()) {
                return ApiResponse.success(List.of());
            }

            List<CommentVO> commentVOs = comments.stream()
                .map(comment -> {
                    CommentVO vo = new CommentVO();
                    vo.setCommentId(comment.getCommentId());
                    vo.setContent(comment.getContent());
                    vo.setLikeCount(comment.getLikeCount());
                    vo.setReplyCount(comment.getReplyCount());
                    vo.setCreatedAt(comment.getCreatedAt());
                    vo.setUpdatedAt(comment.getUpdatedAt());
                    
                    // 设置作者信息
                    User author = userMapper.findById(comment.getAuthorId());
                    if (author != null) {
                        CommentVO.SimpleAuthorVO authorVO = new CommentVO.SimpleAuthorVO();
                        authorVO.setUserId(author.getUserId());
                        authorVO.setUsername(author.getUsername());
                        authorVO.setAvatarUrl(author.getAvatarUrl());
                        vo.setAuthor(authorVO);
                    }
                    
                    // 设置用户操作状态
                    Long currentUserId = requestScopeData.getUserId();
                    if (currentUserId != null) {
                        UserActionVO userActions = new UserActionVO();
                        userActions.setIsLiked(commentLikeMapper.checkIsLiked(currentUserId, comment.getCommentId()));
                        vo.setUserActions(userActions);
                    }
                    
                    return vo;
                })
                .collect(Collectors.toList());

            return ApiResponse.success(commentVOs);
        } catch (Exception e) {
            log.error("获取评论列表失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "获取评论列表失败");
        }
    }

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<EmptyVO> likeComment(Integer commentId) {
        Long userId = requestScopeData.getUserId();

        // 查询评论
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "评论不存在");
        }

        try {
            // 增加评论点赞数
            commentMapper.incrementLikeCount(commentId);
            
            // 发送点赞消息通知
            messageService.createMessage(
                comment.getAuthorId(),  // 接收者是评论作者
                userId,                 // 发送者是点赞用户
                "LIKE",                 // 消息类型是点赞
                commentId,              // 目标ID是评论ID
                "点赞了你的评论"        // 消息内容
            );
            
            return ApiResponse.success(new EmptyVO());
        } catch (Exception e) {
            log.error("点赞评论失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "点赞评论失败");
        }
    }

    @Override
    @NeedLogin
    @Transactional
    public ApiResponse<EmptyVO> unlikeComment(Integer commentId) {
        Long userId = requestScopeData.getUserId();

        // 查询评论
        Comment comment = commentMapper.findById(commentId);
        if (comment == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "评论不存在");
        }

        try {
            // 减少评论点赞数
            commentMapper.decrementLikeCount(commentId);
            return ApiResponse.success(new EmptyVO());
        } catch (Exception e) {
            log.error("取消点赞评论失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "取消点赞评论失败");
        }
    }
} 