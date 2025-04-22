package com.kama.notes.controller;

import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.base.PageVO;
import com.kama.notes.model.dto.message.MessageQueryParams;
import com.kama.notes.model.vo.message.MessageVO;
import com.kama.notes.model.vo.message.UnreadCountByType;
import com.kama.notes.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息控制器
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * 获取消息列表
     *
     * @param params 查询参数
     * @return 消息列表，带分页信息
     */
    @GetMapping
    public ApiResponse<PageVO<MessageVO>> getMessages(@Validated MessageQueryParams params) {
        return messageService.getMessages(params);
    }

    /**
     * 标记消息为已读
     *
     * @param messageId 消息ID
     * @return 空响应
     */
    @PutMapping("/{messageId}/read")
    public ApiResponse<EmptyVO> markAsRead(@PathVariable Integer messageId) {
        return messageService.markAsRead(messageId);
    }

    /**
     * 标记所有消息为已读
     *
     * @return 空响应
     */
    @PutMapping("/read/all")
    public ApiResponse<EmptyVO> markAllAsRead() {
        return messageService.markAllAsRead();
    }

    /**
     * 删除消息
     *
     * @param messageId 消息ID
     * @return 空响应
     */
    @DeleteMapping("/{messageId}")
    public ApiResponse<EmptyVO> deleteMessage(@PathVariable Integer messageId) {
        return messageService.deleteMessage(messageId);
    }

    /**
     * 获取未读消息数量
     *
     * @return 未读消息数量
     */
    @GetMapping("/unread/count")
    public ApiResponse<Integer> getUnreadCount() {
        return messageService.getUnreadCount();
    }

    /**
     * 获取各类型未读消息数量
     *
     * @return 各类型未读消息数量
     */
    @GetMapping("/unread/count/type")
    public ApiResponse<List<UnreadCountByType>> getUnreadCountByType() {
        return messageService.getUnreadCountByType();
    }
} 