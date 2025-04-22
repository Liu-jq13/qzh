package com.kama.notes.model.vo.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 当前登录的用户获取别人的信息的 VO
 */
@Data
public class UserVO {
    /*
     * 用户昵称
     */
    private String username;

    /*
     * 用户性别
     */
    private Integer gender;

    /*
     * 用户头像
     */
    private String avatarUrl;

    /*
     * 用户邮箱
     */
    private String email;

    /*
     * 用户学校
     */
    private String school;

    /*
     * 用户签名
     */
    private String signature;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginAt;
}
