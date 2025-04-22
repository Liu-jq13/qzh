package com.kama.notes.mapper;

import com.kama.notes.model.entity.EmailVerifyCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailVerifyCodeMapper {
    /**
     * 插入验证码记录
     */
    int insert(EmailVerifyCode code);

    /**
     * 查询最新的有效验证码
     */
    EmailVerifyCode findLatestValidCode(@Param("email") String email, @Param("type") String type);

    /**
     * 标记验证码为已使用
     */
    int markAsUsed(@Param("id") Long id);
} 