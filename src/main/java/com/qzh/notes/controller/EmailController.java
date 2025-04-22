package com.qzh.notes.controller;

import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.service.EmailService;
import com.qzh.notes.utils.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/verify-code")
    public ApiResponse<Void> sendVerifyCode(
            @RequestParam @NotBlank @Email String email,
            @RequestParam @NotBlank String type) {
        
        // 检查是否可以发送
        if (!emailService.canSendCode(email)) {
            return ApiResponseUtil.error("发送太频繁，请稍后再试");
        }

        try {
            emailService.sendVerifyCode(email, type);
            return ApiResponseUtil.success(null);
        } catch (Exception e) {
            return ApiResponseUtil.error(e.getMessage());
        }
    }
} 