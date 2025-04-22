package com.kama.notes.scope;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 用于存放当前请求生命周期内的全局数据
 */
@Component
@RequestScope
@Data
public class RequestScopeData {
    private String token;
    private Long userId;
    private boolean isLogin;
    // private boolean isAdmin;
}
