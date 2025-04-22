package com.kama.notes.model.dto.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserQueryParam {
    @Min(value = 1, message = "userId 必须为正整数")
    private Long userId;

    private String account;

    @Length(max = 16, message = "用户名长度不能超过 16 个字符")
    private String username;

    @Min(value = 0, message = "isAdmin 最小只能是 0")
    @Max(value = 1, message = "isAdmin 最大只能是 1")
    private Integer isAdmin;

    @Min(value = 0, message = "isBanned 最小只能是 0")
    @Max(value = 1, message = "isBanned 最大只能是 1")
    private Integer isBanned;

    @NotNull(message = "page 不能为空")
    @Min(value = 1, message = "page 必须为正整数")
    private Integer page;

    @NotNull(message = "pageSize 不能为空")
    @Min(value = 1, message = "pageSize 必须为正整数")
    @Max(value = 200, message = "pageSize 不能超过 200")
    private Integer pageSize;
}
