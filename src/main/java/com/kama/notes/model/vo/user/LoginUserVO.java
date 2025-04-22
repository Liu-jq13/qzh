package com.kama.notes.model.vo.user;

import lombok.Data;

import java.time.LocalDate;

/**
 * LoginUserVO 是当前登录的用户，承载自己的信息的 VO
 * 而 UserVO 是当前登录的用户，获取的其他的用户的信息
 */
@Data
public class LoginUserVO {
    /*
     * 用户ID
     */
    private Long userId;

    /*
     * 用户账号
     */
    private String account;

    /*
     * 用户昵称
     */
    private String username;

    /*
     * 用户性别
     */
    private Integer gender;

    /*
     * 用户生日
     */
    private LocalDate birthday;

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

    /*
     * 是否管理员
     */
    private Integer isAdmin;
}
