package com.kama.notes.service;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.base.PageVO;
import com.kama.notes.model.dto.message.MessageQueryParams;
import com.kama.notes.model.vo.message.MessageVO;
import com.kama.notes.model.vo.message.UnreadCountByType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息服务接口
 */
@Transactional
public interface MessageService {
    /**
     * 创建消息
     *
     * @param receiverId 接收者ID
     * @param senderId 发送者ID
     * @param type 消息类型
     * @param targetId 目标ID
     * @param content 消息内容
     * @return 创建的消息ID
     */
    ApiResponse<Integer> createMessage(Long receiverId, Long senderId, String type, Integer targetId, String content);

    /**
     * 获取消息列表
     *
     * @param params 查询参数
     * @return 消息列表，带分页信息
     */
    ApiResponse<PageVO<MessageVO>> getMessages(MessageQueryParams params);

    /**
     * 标记消息为已读
     *
     * @param messageId 消息ID
     * @return 空响应
     */
    ApiResponse<EmptyVO> markAsRead(Integer messageId);

    /**
     * 标记所有消息为已读
     *
     * @return 空响应
     */
    ApiResponse<EmptyVO> markAllAsRead();

    /**
     * 删除消息
     *
     * @param messageId 消息ID
     * @return 空响应
     */
    ApiResponse<EmptyVO> deleteMessage(Integer messageId);

    /**
     * 获取未读消息数量
     *
     * @return 未读消息数量
     */
    ApiResponse<Integer> getUnreadCount();

    /**
     * 获取各类型未读消息数量
     *
     * @return 各类型未读消息数量
     */
    ApiResponse<List<UnreadCountByType>> getUnreadCountByType();
} 