package com.kama.notes.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TokenApiResponse类是ApiResponse的一个子类，用于处理包含Token的API响应
 * 它提供了一个方法来获取Token
 *
 * @param <T> 泛型参数，表示API响应中数据的类型
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenApiResponse<T> extends ApiResponse<T> {
    /**
     * -- GETTER --
     *  获取Token的方法
     *
     * @return 返回API响应中的Token
     */
    // 用于存储API响应中的Token
    private final String token;

    /**
     * 构造函数，用于初始化TokenApiResponse对象
     *
     * @param code 状态码，表示API响应的状态
     * @param msg  消息，提供关于API响应的额外信息
     * @param data 数据，包含API响应的具体内容
     * @param token Token，包含API响应中的Token
     */
    public TokenApiResponse(Integer code, String msg, T data, String token) {
        super(code, msg, data);
        this.token = token;
    }
}
