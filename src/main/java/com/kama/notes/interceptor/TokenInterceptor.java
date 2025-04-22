package com.kama.notes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kama.notes.scope.RequestScopeData;
import com.kama.notes.utils.JwtUtil;

@Component
public class TokenInterceptor implements HandlerInterceptor
{
    @Autowired
    private RequestScopeData requestScopeData;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 对于每个请求进行拦截，获取请求头中的 token
        // 然后对 token 进行处理，并将 token 携带的信息存储到，在请求周期中全局存在的 requestScopeData 中

        String token = request.getHeader("Authorization");

        if (token == null) {
            requestScopeData.setLogin(false);
            requestScopeData.setToken(null);
            requestScopeData.setUserId(null);
            return true;
        }

        token = token.replace("Bearer ", "");

        if (jwtUtil.validateToken(token)) {
            Long userId = jwtUtil.getUserIdFromToken(token);
            requestScopeData.setUserId(userId);
            requestScopeData.setToken(token);
            requestScopeData.setLogin(true);
        } else {
            requestScopeData.setLogin(false);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
