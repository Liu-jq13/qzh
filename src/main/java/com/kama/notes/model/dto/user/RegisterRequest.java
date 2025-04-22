package com.kama.notes.model.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

/**
 * 用户注册请求DTO
 */
@Data
public class RegisterRequest {

    /**
     * 用户账号
     * 必填，长度 6-32，支持字母、数字和下划线
     */
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 6, max = 32, message = "账号长度必须在 6 到 32 个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "账号只能包含字母、数字和下划线")
    private String account;

    /**
     * 用户昵称
     * 必填，长度 1-16，支持中文、字母、数字、下划线、分隔符
     */
    @NotBlank(message = "用户名不能为空")
    @Size(max = 16, message = "用户名长度不能超过 16 个字符")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9\\-\\.]+$", message = "用户名只能包含中文、字母、数字、下划线、分隔符")
    private String username;

    /**
     * 登录密码
     * 必填，长度 6-32
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在 6 到 32 个字符之间")
    private String password;

    /**
     * 邮箱（选填）
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 验证码（选填，邮箱填写时必填）
     */
    @Size(min = 6, max = 6, message = "验证码长度必须为6位")
    private String verifyCode;
}