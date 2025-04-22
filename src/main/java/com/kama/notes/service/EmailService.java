package com.kama.notes.service;

public interface EmailService {
    /**
     * 发送验证码邮件
     *
     * @param email 目标邮箱
     * @param type 验证码类型（REGISTER/RESET_PASSWORD）
     * @return 生成的验证码
     */
    String sendVerifyCode(String email, String type);

    /**
     * 验证验证码
     *
     * @param email 邮箱
     * @param code 验证码
     * @param type 验证码类型
     * @return 验证是否成功
     */
    boolean verifyCode(String email, String code, String type);

    /**
     * 检查是否可以发送验证码
     * 用于控制发送频率
     *
     * @param email 邮箱
     * @return 是否可以发送
     */
    boolean canSendCode(String email);
} 