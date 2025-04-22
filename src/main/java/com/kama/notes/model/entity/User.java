package com.kama.notes.model.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author Tong
 * @LastChangeDate 2024-12-16 10:27
 * @Version v1.0
 */
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 用户ID（主键）
     * 系统分配不可修改
     */
    private Long userId;

    /**
     * 账号（唯一）
     * 注册时自定义，注册后不可修改
     * 包含数字、字母、下划线
     */
    private String account;

    /**
     * 用户名
     * 可修改，包含中文、字母、数字、下划线
     */
    private String username;

    /**
     * 加密后的登录密码
     */
    private String password;

    /**
     * 用户性别
     * 1=男，2=女，3=保密
     */
    private Integer gender;

    /**
     * 用户生日
     */
    private LocalDate birthday;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 邮箱是否验证
     */
    private Boolean emailVerified;

    /**
     * 用户学校
     */
    private String school;

    /**
     * 用户签名
     */
    private String signature;

    /**
     * 封禁状态
     * 0=未封禁，1=已封禁
     */
    private Integer isBanned;

    /**
     * 管理员状态
     * 0=普通用户，1=管理员
     */
    private Integer isAdmin;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
