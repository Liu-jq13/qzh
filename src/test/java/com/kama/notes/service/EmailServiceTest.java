package com.kama.notes.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testSendVerifyCode() {
        String email = "example@qq.com"; // 使用实际的QQ邮箱
        String type = "REGISTER";
        
        String verifyCode = emailService.sendVerifyCode(email, type);
        System.out.println("Generated verify code: " + verifyCode);
        
        // 验证验证码
        boolean verified = emailService.verifyCode(email, verifyCode, type);
        assert verified : "Verification should succeed with correct code";
    }

    @Test
    public void testSendFrequencyLimit() {
        String email = "example@qq.com"; // 使用实际的QQ邮箱
        
        // 第一次发送应该成功
        assert emailService.canSendCode(email) : "Should be able to send first code";
        emailService.sendVerifyCode(email, "REGISTER");
        
        // 第二次发送应该被限制
        assert !emailService.canSendCode(email) : "Should not be able to send second code immediately";
    }
} 
