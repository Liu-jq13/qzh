package com.kama.notes.model.dto.user;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * 用户信息更新请求 DTO
 */
@Data
public class UpdateUserRequest {
    /**
     * 用户昵称
     * 非必填，长度在 1-16 个字符，允许中文、字母、数字、下划线。
     */
    @Size(min = 1, max = 16, message = "用户名长度必须在 1 到 16 个字符之间")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$", message = "用户名只能包含中文、字母、数字和下划线")
    private String username;

    /**
     * 用户性别
     * 非必填，取值范围：1=男，2=女，3=保密。
     */
    @Min(value = 1, message = "性别取值无效")
    @Max(value = 3, message = "性别取值无效")
    private Integer gender;

    /**
     * 用户生日
     * 非必填，必须是过去的日期。
     */
    @Past(message = "生日必须是一个过去的日期")
    private LocalDate birthday;

    /**
     * 用户头像
     * 非必填，必须是有效的 URL。
     */
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "头像地址必须是有效的 URL")
    private String avatarUrl;

    /**
     * 用户邮箱
     * 非必填，必须是有效的邮箱地址。
     */
    @Email(message = "邮箱格式无效")
    private String email;

    /**
     * 用户学校
     * 非必填，长度在 1-64 个字符。
     */
    @Size(max = 64, message = "学校名称长度不能超过 64 个字符")
    private String school;

    /**
     * 用户签名
     * 非必填，长度在 1-128 个字符。
     */
    @Size(max = 128, message = "签名长度不能超过 128 个字符")
    private String signature;
}
