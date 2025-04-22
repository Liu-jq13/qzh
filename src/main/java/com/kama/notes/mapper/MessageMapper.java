package com.kama.notes.mapper;

import com.kama.notes.model.dto.message.MessageQueryParams;
import com.kama.notes.model.entity.Message;
import com.kama.notes.model.vo.message.UnreadCountByType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息Mapper接口
 */
@Mapper
public interface MessageMapper {
    /**
     * 插入消息
     *
     * @param message 消息实体
     * @return 影响行数
     */
    int insert(Message message);

    /**
     * 根据参数查询消息列表
     *
     * @param userId 用户ID
     * @param params 查询参数
     * @param offset 偏移量
     * @return 消息列表
     */
    List<Message> selectByParams(@Param("userId") Long userId, @Param("params") MessageQueryParams params, @Param("offset") int offset);

    /**
     * 统计符合条件的消息数量
     *
     * @param userId 用户ID
     * @param params 查询参数
     * @return 消息数量
     */
    int countByParams(@Param("userId") Long userId, @Param("params") MessageQueryParams params);

    /**
     * 标记消息为已读
     *
     * @param messageId 消息ID
     * @param userId 用户ID
     * @return 影响行数
     */
    int markAsRead(@Param("messageId") Integer messageId, @Param("userId") Long userId);

    /**
     * 标记所有消息为已读
     *
     * @param userId 用户ID
     * @return 影响行数
     */
    int markAllAsRead(@Param("userId") Long userId);

    /**
     * 删除消息
     *
     * @param messageId 消息ID
     * @param userId 用户ID
     * @return 影响行数
     */
    int deleteMessage(@Param("messageId") Integer messageId, @Param("userId") Long userId);

    /**
     * 统计未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    int countUnread(@Param("userId") Long userId);

    /**
     * 按类型统计未读消息数量
     *
     * @param userId 用户ID
     * @return 各类型未读消息数量
     */
    List<UnreadCountByType> countUnreadByType(@Param("userId") Long userId);
} 