package com.kama.notes.service.impl;

import com.kama.notes.mapper.MessageMapper;
import com.kama.notes.mapper.UserMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.base.PageVO;
import com.kama.notes.model.dto.message.MessageQueryParams;
import com.kama.notes.model.entity.Message;
import com.kama.notes.model.entity.User;
import com.kama.notes.model.vo.message.MessageVO;
import com.kama.notes.model.vo.message.UnreadCountByType;
import com.kama.notes.service.MessageService;
import com.kama.notes.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 消息服务实现类
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final UserMapper userMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    // Redis键前缀
    private static final String UNREAD_COUNT_KEY = "message:unread:count:";
    private static final String UNREAD_COUNT_BY_TYPE_KEY = "message:unread:type:";
    private static final String MESSAGE_CACHE_KEY = "message:detail:";

    @Override
    public ApiResponse<Integer> createMessage(Long receiverId, Long senderId, String type, Integer targetId, String content) {
        log.info("开始创建消息通知: receiverId={}, senderId={}, type={}, targetId={}, content={}", 
                receiverId, senderId, type, targetId, content);
        
        try {
            Message message = new Message();
            message.setReceiverId(receiverId);
            message.setSenderId(senderId);
            message.setType(type);
            message.setTargetId(targetId);
            message.setContent(content);
            message.setIsRead(false);
            message.setCreatedAt(LocalDateTime.now());
            message.setUpdatedAt(LocalDateTime.now());

            int rows = messageMapper.insert(message);
            log.info("消息通知创建结果: messageId={}, 影响行数={}", message.getMessageId(), rows);
            
            // 清除相关缓存
            clearMessageCache(receiverId);
            
            return ApiResponse.success(message.getMessageId());
        } catch (Exception e) {
            log.error("创建消息通知失败", e);
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "创建消息通知失败: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<PageVO<MessageVO>> getMessages(MessageQueryParams params) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        
        // 获取总记录数
        int total = messageMapper.countByParams(currentUserId, params);
        
        // 计算偏移量
        int offset = (params.getPage() - 1) * params.getPageSize();
        
        // 获取当前页数据
        List<Message> messages = messageMapper.selectByParams(currentUserId, params, offset);

        // 转换为VO对象
        List<MessageVO> messageVOs = messages.stream().map(message -> {
            MessageVO vo = new MessageVO();
            vo.setMessageId(message.getMessageId());
            vo.setType(message.getType());
            vo.setTargetId(message.getTargetId());
            vo.setContent(message.getContent());
            vo.setIsRead(message.getIsRead());
            vo.setCreatedAt(message.getCreatedAt());

            // 从缓存获取发送者信息
            User sender = getUserFromCache(message.getSenderId());
            if (sender != null) {
                MessageVO.SimpleUserVO senderVO = new MessageVO.SimpleUserVO();
                senderVO.setUserId(sender.getUserId());
                senderVO.setUsername(sender.getUsername());
                senderVO.setAvatarUrl(sender.getAvatarUrl());
                vo.setSender(senderVO);
            }

            return vo;
        }).collect(Collectors.toList());

        // 创建分页结果
        PageVO<MessageVO> pageVO = PageVO.of(messageVOs, params.getPage(), params.getPageSize(), total);
        return ApiResponse.success(pageVO);
    }

    @Override
    public ApiResponse<EmptyVO> markAsRead(Integer messageId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        messageMapper.markAsRead(messageId, currentUserId);
        
        // 清除相关缓存
        clearMessageCache(currentUserId);
        
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<EmptyVO> markAllAsRead() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        messageMapper.markAllAsRead(currentUserId);
        
        // 清除相关缓存
        clearMessageCache(currentUserId);
        
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<EmptyVO> deleteMessage(Integer messageId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        messageMapper.deleteMessage(messageId, currentUserId);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<Integer> getUnreadCount() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        String cacheKey = UNREAD_COUNT_KEY + currentUserId;
        
        // 尝试从缓存获取
        Integer count = (Integer) redisTemplate.opsForValue().get(cacheKey);
        if (count == null) {
            // 缓存未命中，从数据库查询
            count = messageMapper.countUnread(currentUserId);
            // 将结果存入缓存，设置5分钟过期
            redisTemplate.opsForValue().set(cacheKey, count, 5, TimeUnit.MINUTES);
        }
        
        return ApiResponse.success(count);
    }

    @Override
    public ApiResponse<List<UnreadCountByType>> getUnreadCountByType() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        String cacheKey = UNREAD_COUNT_BY_TYPE_KEY + currentUserId;
        
        // 尝试从缓存获取
        @SuppressWarnings("unchecked")
        List<UnreadCountByType> result = (List<UnreadCountByType>) redisTemplate.opsForValue().get(cacheKey);
        if (result == null) {
            // 缓存未命中，从数据库查询
            result = messageMapper.countUnreadByType(currentUserId);
            // 将结果存入缓存，设置5分钟过期
            redisTemplate.opsForValue().set(cacheKey, result, 5, TimeUnit.MINUTES);
        }
        
        return ApiResponse.success(result);
    }

    /**
     * 从缓存获取用户信息
     */
    private User getUserFromCache(Long userId) {
        String cacheKey = "user:detail:" + userId;
        User user = (User) redisTemplate.opsForValue().get(cacheKey);
        if (user == null) {
            user = userMapper.findById(userId);
            if (user != null) {
                redisTemplate.opsForValue().set(cacheKey, user, 30, TimeUnit.MINUTES);
            }
        }
        return user;
    }

    /**
     * 清除用户相关的消息缓存
     */
    private void clearMessageCache(Long userId) {
        redisTemplate.delete(UNREAD_COUNT_KEY + userId);
        redisTemplate.delete(UNREAD_COUNT_BY_TYPE_KEY + userId);
    }
} 