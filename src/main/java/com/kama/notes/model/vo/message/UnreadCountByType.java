package com.kama.notes.model.vo.message;

import lombok.Data;

/**
 * 各类型未读消息数量
 */
@Data
public class UnreadCountByType {
    /**
     * 消息类型
     */
    private String type;

    /**
     * 未读数量
     */
    private Integer count;
} 